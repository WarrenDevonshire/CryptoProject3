import org.jetbrains.annotations.Contract;

import javax.crypto.IllegalBlockSizeException;

public class RSA {
    /**
     *
     * @param e
     * @param m
     * @return
     * @author Warren Devonshire
     */
    @Contract(pure = true)
    public static long inverse(long e, long m)throws ArithmeticException{
        if(m < 0 || e < 0) throw new IllegalArgumentException("Arguments must be positive!");

        long result[] = egcd(e,m);
        long d = result[0];
        if(result[2] != 1) throw new ArithmeticException("Numbers are not relatively prime.");

        while(d < 0) d += m;

        return d;
    }

    /**
     * The Extended Euclidean Algorithm
     * @param u
     * @param v
     * @return Array {u0,u1,u2} where the equation: u0*u + u1*v = u2 = gcd(u,v) holds true.
     * u0 is the inverse of u, and u2 is the gcd of u and v.
     * @author Warren Devonshire
     */
    @Contract(pure = true)
    public static long[] egcd(long u, long v){
        u = Math.abs(u);
        v = Math.abs(v);
        long q;

        long u0 = 1,u1 = 0,u2 = u;
        long v0 = 0,v1 = 1,v2 = v;
        long t0,t1,t2;

        while(v2 != 0){
            q = u2 / v2;

            t0 = u0 - q*v0;
            t1 = u1 - q*v1;
            t2 = u2 - q*v2;

            u0 = v0;
            u1 = v1;
            u2 = v2;

            v0 = t0;
            v1 = t1;
            v2 = t2;
        }

        return new long[]{u0,u1,u2};
    }

    /**
     *
     * @param u
     * @param v
     * @return
     * @author Warren Devonshire
     */
    @Contract(pure = true)
    public static long gcd(long u, long v){
        u = Math.abs(u);
        v = Math.abs(v);
        long r;

        while(v != 0){
            r = u % v;
            u = v;
            v = r;
        }
        return u;
    }

    /**
     *
     * @param b Base
     * @param p Exponent
     * @param m Modulus
     * @return
     * @throws ArithmeticException
     * @throws IllegalArgumentException
     * @author Warren Devonshire
     */
    @Contract(pure = true)
    public static long modPower(long b, long p, long m) throws ArithmeticException, IllegalArgumentException {
        //rename variables for code clarity
        long base = b;
        long exponent = p;
        long modulus = m;

        //check for illegal arguments
        if(modulus <= 0) throw new IllegalArgumentException("Modulus cannot be zero!");
        if(exponent < 0) throw new IllegalArgumentException("Exponent cannot be less than zero!");

        //If a modulus is 1 the answer is always zero
        if(modulus == 1) return 0;

        //Throws ArithmeticException if overflow
        Math.multiplyExact(modulus-1, modulus-1);

        //check to see if base is less than zero, if true make base positive.
        while(base < 0) base += modulus;

        //Right to Left Binary Exponentiation
        long result = 1;
        base = base % modulus;
        while(exponent > 0){
            if(exponent % 2 == 1)
                result = (result * base) % modulus;
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }
}
