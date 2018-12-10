import java.util.Random;

/**
 * Used to test public-key crypto-system, RSA
 *
 * @author Matt Halloran
 * @verson 11-30-18
 */
public class Person {
    //used to fill the last block if the message is not a multiple of the block size
    private char stufferChar = 0x0;

    private final byte blockSize = 2;

    //public modulus. m = p*q, where p and q are both primes
    private long m;

    //selected. Relatively prime to n
    private long e;

    //decryption key. Must never be shared
    private long d;

    //n = (p-1)(q-1)
    private long n;

    /**
     * Generate a public key for this person, consisting of exponent,e, and modulus, m.
     * Generate a private key, consisting of an exponent, d.
     * Provide access to the public key only.
     */
    public Person() {
        Random rand = new Random();
        int minPrimeSize = (int) Math.pow(2, 4 * blockSize);
        int maxPrimeSize = (int) Math.pow(2, 15);
        long p = RSA.randPrime(minPrimeSize, maxPrimeSize, rand);
        long q = RSA.randPrime(minPrimeSize, maxPrimeSize, rand);
        m = p * q;
        n = (p - 1) * (q - 1);
        e = RSA.relPrime(n, rand);
        d = RSA.inverse(e, n);
    }

    /**
     * @return The person's public modulus
     */
    public long getM() {
        return m;
    }

    /**
     * @return The person's public encryption exponent
     */
    public long getE() {
        return e;
    }

    /**
     * Encrypt a plain text message to she.
     * y = x^e (mod m)
     *
     * @param msg The plain text to be encrypted
     * @param she The Person being sent the cipher text
     * @return The cipher text as an array of longs
     */
    public long[] encryptTo(String msg, Person she) throws ArithmeticException {
        //Makes message length a multiple of the block size
        int stufferCharsLength = msg.length() % blockSize;
        for (int i = 0; i < stufferCharsLength; i++) 
            msg += stufferChar;

        //Converts msg string to long array
        long[] msgAsArray = new long[msg.length() / blockSize];
        for (int i = 0; i < msgAsArray.length; i++) 
        	msgAsArray[i] = RSA.toLong(msg, i*2);

        //Encrypts each long in the message
        for (int i = 0; i < msgAsArray.length; i++) 
            msgAsArray[i] = RSA.modPower(msgAsArray[i], she.getE(), she.getM());

        return msgAsArray;
    }

    /**
     * Decrypt the cipher text
     * x = y^d (mod m)
     *
     * @param cipher The cipher text to be decrypted
     * @return The string of plain text
     */
    public String decrypt(long[] cipher) throws ArithmeticException {
        //Decrypts each long in the message
        for (int i = 0; i < cipher.length; i++)
            cipher[i] = RSA.modPower(cipher[i], d, m);

        //Converts long array to char array
        String plainText = new String();
        for(int i = 0; i < cipher.length; i++)
        	plainText += RSA.longTo2Chars(cipher[i]);
        return new String(plainText).replace("\0", "");
    }
}