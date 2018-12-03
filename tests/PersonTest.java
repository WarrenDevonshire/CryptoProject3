import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void encryptTo() {
    }

    @Test
    void decrypt() {
    }

    @Test
    void encryptEqualsDecrypt(){
        Person person = new Person();
        Random r = new Random();
        int stringSize = Math.abs(r.nextInt(100));
        StringBuilder msgBuilder = new StringBuilder(stringSize);
        for(int j = 0; j < stringSize; j++) {
            msgBuilder.append(r.nextInt(10));
        }
        String msg = msgBuilder.toString();

        long[] cipher = person.encryptTo(msg, person);

        assertEquals(msg, person.decrypt(cipher));
    }
}