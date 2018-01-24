package by.epam.hr.model;

public class Test {
    private static final String REGEXP_ROLE = "(candidate|employer|admin)";
    private static final String REGEXP_EMAIL = ".+@.+\\..+";
    private static final String REGEXP_PASSWORD = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
    String password ="!QAZ1qaz";

    public static void main(String[] args) {
        String password ="!QAZ1qaz";
        String email ="dsa@dsa.ds";
        String role ="admin";
        System.out.println(new Test().cript(role).equals(new Test().cript(email)));
        System.out.println(new Test().cript(role));
    }

    public String cript(String pass){
        char[] array = pass.toCharArray();
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            out.append((array[i] *31) >> 5);
        }
        return out.toString();
    }

}
