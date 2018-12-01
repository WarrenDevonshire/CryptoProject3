import java.util.Random;

/**
 * Used to test public-key crypto-system, RSA
 * @author Matt Halloran
 * @verson 11-30-18
 */
public class Person 
{
	//used to fill the last block if the message is not a multiple of the block size
	private char stufferChar = 0x0;
	
	//minimum is 2, max is 4
	private byte blockSize = 1;
	
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
	public Person()
	{
		Random rand = new Random();
		int minPrimeSize = (int)Math.pow(2, 8*blockSize); //2^(block size). This works because ascii 
														  //characters are 8 bits, but the max ascii value is 127
		int maxPrimeSize = (int)Math.pow(2, 32);		  //2^(length of int)
		long p = 11353;//RSA.randPrime(minPrimeSize, maxPrimeSize, rand);
		long q = 6917;//RSA.randPrime(minPrimeSize, maxPrimeSize, rand);
		m = p*q;
		n = (p-1)*(q-1);
		e = 78510427;//RSA.relPrime(n, rand);
		d = 28856311;//RSA.inverse(e, m);
	}
	
	/**
	 * @return The person's public modulus
	 */
	public long getM()
	{
		return m;
	}
	
	/**
	 * @return The person's public encryption exponent
	 */
	public long getE()
	{
		return e;
	}
	
	/**
	 * Encrypt a plain text message to she.
	 * y = x^e (mod m)
	 * @param msg The plain text to be encrypted
	 * @param she The Person being sent the cipher text
	 * @return The cipher text as an array of longs
	 */
	public long[] encryptTo(String msg, Person she) throws ArithmeticException
	{
		long[] msgAsArray = new long[(int)Math.ceil(msg.length()/(double)blockSize)];
		
		int stufferCharsLength = msg.length() % blockSize;
		for(int i = 0; i < stufferCharsLength; i++)
		{
			msg += stufferChar;
		}
		//Converts msg string to long array
		char[] messageChar = msg.toCharArray();
		int current = 0;
		for(int i = 0; i < msgAsArray.length; i++)
		{
			for(int j = 0; j < blockSize; j++)
			{
				msgAsArray[i] <<= 8; //shifts one byte
				msgAsArray[i] += (long)messageChar[current++];
			}
		}
		
		//Encrypts each long in the message
		for(int i = 0; i < msgAsArray.length; i++)
		{
			msgAsArray[i] = RSA.modPower(msgAsArray[i], she.getE(), she.getM());
		}
		
		return msgAsArray;
	}
	
	/**
	 * Decrypt the cipher text
	 * x = y^d (mod m)
	 * @param cipher The cipher text to be decrypted
	 * @return The string of plain text
	 */
	public String decrypt(long[] cipher) throws ArithmeticException
	{	
		//Decrypts each long in the message
		for(int i = 0; i < cipher.length; i++)
		{
			cipher[i] = RSA.modPower(cipher[i], d, m);
		}
		
		//Converts long array to char array
		char[] messageChar = new char[cipher.length * blockSize];
		int current = messageChar.length-1;
		for(int i = cipher.length-1; i >= 0; i--)
		{
			long byteExtractor = 255;
			for(int j = 0; j < blockSize; j++)
			{
				messageChar[current--] = (char)(cipher[i] & byteExtractor);
				byteExtractor <<= 8;
			}
		}
		
		return messageChar.toString();
	}
}
