import org.junit.jupiter.api.Test;

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
        String msg = "Hello, World!";

        long[] cipher = person.encryptTo(msg, person);

        assertEquals(msg, person.decrypt(cipher));
    }
}