import org.jetbrains.annotations.Contract;

public class RSA {


    @Contract(pure = true)
    public static long inverse(long e, long m){
        //rename variables for code clarity
        long exponent = e;
        long modulus = m;


        return 0;
    }


    @Contract(pure = true)
    public static long modPower(long b, long p, long m) throws ArithmeticException{
        //rename variables for code clarity
        long base = b;
        long exponent = p;
        long modulus = m;

        if(modulus == 1) return 0;

        //Throw ArithmeticException if overflow
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
