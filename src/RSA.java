import org.jetbrains.annotations.Contract;

public class RSA {


    /**
     *
     * @param e
     * @param m
     * @return
     */
    @Contract(pure = true)
    public static long inverse(long e, long m){
        //rename variables for code clarity
        long exponent = e;
        long modulus = m;


        return 0;
    }

    /**
     * The Extended Euclidean Algorithm
     * @param u
     * @param v
     * @return Array {u0,u1,u2} where the equation: u0*u + u1*v = u2 = gcd(u,v) holds true.
     * u0 is the inverse of u, and u2 is the gcd of u and v.
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
     * @param b
     * @param p
     * @param m
     * @return
     * @throws ArithmeticException
     */
    @Contract(pure = true)
    public static long modPower(long b, long p, long m) throws ArithmeticException{
        //rename variables for code clarity
        long base = b;
        long exponent = p;
        long modulus = m;

        if(modulus == 1) return 0;

        //Throws ArithmeticException if overflow
        Math.multiplyExact(modulus-1, modulus-1);

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
