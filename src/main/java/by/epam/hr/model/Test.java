package by.epam.hr.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private static final String REGEXP_ROLE = "(candidate|employer|admin)";
    private static final String REGEXP_EMAIL = ".+@.+\\..+";
    private static final String REGEXP_PASSWORD = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
    String password ="!QAZ1qaz";

    public static void main(String[] args) {
        String password ="!QAZ1qaz";
        String email ="dsa@dsa.ds";
        String role = String.valueOf(true);
        List<String> a = new ArrayList<>();
        a.add(password);
        a.add(email);
        a.add(role);
        String json = new Gson().toJson(a);
        System.out.println(json);

    }


}
