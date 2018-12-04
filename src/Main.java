public class Main {
    public static void main(String[] args) {
        Person a = new Person();
        Person b = new Person();
        long[] cipher = a.encryptTo("hello", b);
        String plaintext = b.decrypt(cipher);
    }
}
