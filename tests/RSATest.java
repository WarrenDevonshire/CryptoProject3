import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {
    @Test
    void inverse() {
    }

    @Test
    void modPower() {
        Random rand = new Random();
        int base = rand.nextInt(Integer.MAX_VALUE);
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
        long exponent = rand.nextLong();
        long modulus = rand.nextLong();

        assertThrows(ArithmeticException.class, () -> {
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