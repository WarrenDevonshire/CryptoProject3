import static java.lang.Math.sqrt;
import java.util.PrimitiveIterator;

/**
 * Methods for Part 3 Subdivision.
 * Waiting to make driver/testing until full code is compiled.
 * @author Jacob Caggese
 */
public class RSA {

	/**
	 * Driver for RSA
	 * @param args
	 * @author Jacob Caggese
	 */
	public static void main (String args[])
	{ 	
		Person Alice = new Person();
		Person Bob = new Person();

		String msg = new String ("Bob, let's have lunch."); 	// message to be sent to Bob
		long []  cipher;
		cipher =  Alice.encryptTo(msg, Bob);			// encrypted, with Bob's public key

		System.out.println ("Message is: " + msg);
		System.out.println ("Alice sends:");
		show (cipher);

		System.out.println ("Bob decodes and reads: " + Bob.decrypt (cipher));	// decrypted,
									// with Bob's private key.
		System.out.println ();

		msg = new String ("No thanks, I'm busy");
		cipher = Bob.encryptTo (msg, Alice);
		
		System.out.println ("Message is: " + msg);
		System.out.println ("Bob sends:");
		show (cipher);

		System.out.println ("Alice decodes and reads: " + Alice.decrypt (cipher));
	}

	/**
	 * Generate a random prime number in specified range
	 * Utilizes pretty much bruteforce technique to check primes
	 * @param x Lower bound
	 * @param y Upper bound
	 * @param rand Random number generator
	 * @author Jacob Caggese
	 */
	public static long randPrime(long x, long y, java.util.Random rand) {
		PrimitiveIterator.OfLong longs = rand.longs(x,y).distinct().iterator();
		long longCheck;
		while (longs.hasNext()) {
			longCheck = longs.nextLong();
			double sqrtLong = sqrt(longCheck);
			boolean canBePrime = true;
			if (longCheck % 2 != 0) {
				for (int i = 3; i < sqrtLong && canBePrime; i += 2) {
					if ((longCheck % i) == 0)
						canBePrime = false;
				}
				if (canBePrime) // only true if no factors
					return longCheck;
			}
			// After some number of iterations, return 0 or throw exception
		}
		return 0;
	}

	/**
	 * Generate a number relatively prime to passed in number
	 * @param num Number to find relPrime number of
	 * @param rand RNG
	 * @author Jacob Caggese
	 */
	public static long relPrime(long num, java.util.Random rand) {
		PrimitiveIterator.OfLong longs = rand.longs(1,num).distinct().iterator();
		long longCheck;
		while (longs.hasNext()) {
			longCheck = longs.nextLong();
			boolean canBePrime = true;
			if (num % longCheck != 0) { // if random long isn't itself a factor of num
				// check for mutual factors
				if (longCheck % 2 == 0) { //check if both numbers are even first
					if (num % 2 == 0)
						canBePrime = false;
				}
				long lcMaxFact = longCheck / 2; //maximum possible integer factor is num/2
				for (long i = 3; i < lcMaxFact && canBePrime; i += 2) {
					if ((longCheck % i) == 0) {
						if ((num % i) == 0)
							canBePrime = false;
					}
				}
				if (canBePrime) // only true if no factors
					return longCheck;
			}
			// After some number of iterations, return 0 or throw exception
		}
		return 0;
	}
	
	/**
	 * Converts a long to 2 ASCII characters
	 * @param x Long to convert
	 * @return String of 2 characters
	 * @author Jacob Caggese
	 */
	public static java.lang.String longTo2Chars(long twoChar) {
		char[] retString = {(char) (twoChar >> 32), (char) twoChar}; //left half + right half
		return new String(retString); 
	}
	
	/**
	 * Display array of longs
	 * @param cipher Array to display
	 * @author Jacob Caggese
	 */
	public static void show(long[] cipher) {
		for (int i = 0; i < cipher.length; i++) {
			System.out.print(cipher[i] + " ");
		}
		System.out.println();
	}
	
	/**
	 * Convert a 2-character String to a long
	 * @param msg String to convert
	 * @param p Beginning position of two chars in message to convert
	 * @return long representing two characters
	 * @author Jacob Caggese
	 */
	public static long toLong(java.lang.String msg, int p) {
		char[] msgChar = msg.toCharArray();
		try {
			return (long) msgChar[p]<<32 | msgChar[p+1]; //left char appended to right
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			return (long) msgChar[p]<<32;
		}
	}

	/**
	 * @param e
	 * @param m
	 * @return
	 * @author Warren Devonshire
	 */
	public static long inverse(long e, long m) throws ArithmeticException {
		if (m < 0 || e < 0) throw new IllegalArgumentException("Arguments must be positive!");

		long result[] = egcd(e, m);
		long d = result[0];
		if (result[2] != 1) throw new ArithmeticException("Numbers are not relatively prime.");

		while (d < 0) d += m;

		return d;
	}

	/**
	 * The Extended Euclidean Algorithm
	 *
	 * @param u
	 * @param v
	 * @return Array {u0,u1,u2} where the equation: u0*u + u1*v = u2 = gcd(u,v) holds true.
	 * u0 is the inverse of u, and u2 is the gcd of u and v.
	 * @author Warren Devonshire
	 */
	public static long[] egcd(long u, long v) {
		u = Math.abs(u);
		v = Math.abs(v);
		long q;

		long u0 = 1, u1 = 0, u2 = u;
		long v0 = 0, v1 = 1, v2 = v;
		long t0, t1, t2;

		while (v2 != 0) {
			q = u2 / v2;

			t0 = u0 - q * v0;
			t1 = u1 - q * v1;
			t2 = u2 - q * v2;

			u0 = v0;
			u1 = v1;
			u2 = v2;

			v0 = t0;
			v1 = t1;
			v2 = t2;
		}

		return new long[]{u0, u1, u2};
	}

	/**
	 * @param u
	 * @param v
	 * @return
	 * @author Warren Devonshire
	 */
	public static long gcd(long u, long v) {
		u = Math.abs(u);
		v = Math.abs(v);
		long r;

		while (v != 0) {
			r = u % v;
			u = v;
			v = r;
		}
		return u;
	}

	/**
	 * @param b Base
	 * @param p Exponent
	 * @param m Modulus
	 * @return
	 * @throws ArithmeticException
	 * @throws IllegalArgumentException
	 * @author Warren Devonshire
	 */
	public static long modPower(long b, long p, long m) throws ArithmeticException, IllegalArgumentException {
		//rename variables for code clarity
		long base = b;
		long exponent = p;
		long modulus = m;

		//check for illegal arguments
		if (modulus <= 0) throw new IllegalArgumentException("Modulus cannot be zero!");
		if (exponent < 0) throw new IllegalArgumentException("Exponent cannot be less than zero!");

		//If a modulus is 1 the answer is always zero
		if (modulus == 1) return 0;

		//Throws ArithmeticException if overflow
		Math.multiplyExact(modulus - 1, modulus - 1);

		//check to see if base is less than zero, if true make base positive.
		while (base < 0) base += modulus;

		//Right to Left Binary Exponentiation
		long result = 1;
		base = base % modulus;
		while (exponent > 0) {
			if (exponent % 2 == 1)
				result = (result * base) % modulus;
			exponent = exponent >> 1;
		base = (base * base) % modulus;
		}
		return result;
	}
}
