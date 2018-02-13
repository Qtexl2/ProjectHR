package by.epam.hr.encryption;

/**
 * The Class EncryptionPassword.
 */
public class EncryptionPassword {

    /**
     * Encrypt.
     *
     * @param pass the pass
     * @return the string
     */
    public static String encrypt(String pass){
        char[] array = pass.toCharArray();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            out.append((array[i] *31) >> 5);
        }
        return out.toString();
    }
}
