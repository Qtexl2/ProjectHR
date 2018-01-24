package by.epam.hr.encryption;

public class EncryptionPassword {

    public static String encrypt(String pass){
        char[] array = pass.toCharArray();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            out.append((array[i] *31) >> 5);
        }
        return out.toString();
    }
}
