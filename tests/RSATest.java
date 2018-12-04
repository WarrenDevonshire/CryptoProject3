import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {
    @Test
    void inverse() {
        Random rand = new Random();
        long e = Math.abs(rand.nextLong());
        long m = Math.abs(rand.nextLong());
        BigInteger bigE = BigInteger.valueOf(e);
        BigInteger bigM = BigInteger.valueOf(m);

        BigInteger gcd = bigE.gcd(bigM);
        BigInteger one = BigInteger.valueOf(1);

        if(gcd.equals(one)){//e and m are relatively prime.
            assertEquals(String.valueOf(RSA.inverse(e,m)), bigE.modInverse(bigM).toString());
        }else{//should throw exceptions.
            assertThrows(ArithmeticException.class, () -> {
                RSA.inverse(e,m);
            });
        }
    }

    @Test
    void inverseThrowsIllegalArgumentException(){
        Random rand = new Random();
        long e = rand.nextLong();
        long m = rand.nextLong();
        BigInteger bigE = BigInteger.valueOf(e);
        BigInteger bigM = BigInteger.valueOf(m);

        BigInteger gcd = bigE.gcd(bigM);
        BigInteger one = BigInteger.valueOf(1);

        if(e < 0 || m < 0){//e and m are not positive
            assertThrows(IllegalArgumentException.class, () -> {
                RSA.inverse(e,m);
            });
        }else{
            if(gcd.equals(one)){//e and m are relatively prime.
                assertEquals(String.valueOf(RSA.inverse(e,m)), bigE.modInverse(bigM).toString());
            }else{//should throw exceptions.
                assertThrows(ArithmeticException.class, () -> {
                    RSA.inverse(e,m);
                });
            }
        }
    }

    @Test
    void modPower() {
        Random rand = new Random();
        int base = rand.nextInt();
        int exponent = rand.nextInt(Integer.MAX_VALUE);
        int modulus = rand.nextInt(Integer.MAX_VALUE);

        BigInteger bigBase, bigExponent, bigModulus;

        bigBase = new BigInteger(String.valueOf(base));
        bigExponent = new BigInteger(String.valueOf(exponent));
        bigModulus = new BigInteger(String.valueOf(modulus));

        assertEquals(String.valueOf(RSA.modPower(base, exponent, modulus)), bigBase.modPow(bigExponent, bigModulus).toString());
    }

    @Test
    void modPowerThrowsArithmeticException(){
        Random rand = new Random();
        long base = rand.nextLong();
        long exponent = Math.abs(rand.nextLong());
        long modulus = Math.abs(rand.nextLong());

        assertThrows(ArithmeticException.class, () -> {
            RSA.modPower(base, exponent, modulus);
        });
    }

    @Test
    void modPowerThrowsIllegalArgumentExceptionForModulus(){
        Random rand = new Random();
        long base = rand.nextLong();
        long exponent = Math.abs(rand.nextLong());
        long modulus = -Math.abs(rand.nextLong());

        assertThrows(IllegalArgumentException.class, () -> {
            RSA.modPower(base, exponent, modulus);
        });
    }

    @Test
    void modPowerThrowsIllegalArgumentExceptionForExponent(){
        Random rand = new Random();
        long base = rand.nextLong();
        long exponent = -Math.abs(rand.nextLong());
        long modulus = Math.abs(rand.nextLong());

        assertThrows(IllegalArgumentException.class, () -> {
            RSA.modPower(base, exponent, modulus);
        });
    }

    @Test
    void gcd() {
        Random rand = new Random();
        long u = rand.nextLong();
        long v = rand.nextLong();
        BigInteger bigU = BigInteger.valueOf(u);
        BigInteger bigV = BigInteger.valueOf(v);

        assertEquals(String.valueOf(RSA.gcd(u,v)), bigU.gcd(bigV).toString());
    }

    @Test
    void egcd() {
        Random rand = new Random();
        long u = rand.nextLong();
        long v = rand.nextLong();
        BigInteger bigU = BigInteger.valueOf(u);
        BigInteger bigV = BigInteger.valueOf(v);

        assertEquals(String.valueOf(RSA.egcd(u,v)[2]), bigU.gcd(bigV).toString());
    }
}