package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;
import by.epam.hr.service.ProfileService;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_ROLE = "role";
    private static final String REGEXP_ROLE = "(candidate|employer|admin)";
    private static final String REGEXP_EMAIL = ".+@.+\\..+";
    private static final String REGEXP_PASSWORD = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
    private ProfileService profileService;
//TODO ПОСЛЕ АВТОРИЗАЦИИ СДЛЕАТЬ СОЗДАНИЕ АДМИНОВ

    public RegisterCommand(){
        profileService = new ProfileService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile;
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String role = request.getParameter(PARAM_ROLE);
        boolean statusReg = false;
        String status = null;
        if(password == null || email == null || role == null){
            status = "Password and email are empty. This field is required";
        }
        else if(!(password.matches(REGEXP_PASSWORD) && email.matches(REGEXP_EMAIL) && role.matches(REGEXP_ROLE))){

            status = "Wrong Email or password format";
        }
        else if(password.length() > 30 && email.length() > 50){
            status = "Wrong Email or password format";
        }
        else{
            try {
                profile = new Profile(email,password,Role.valueOf(role.toUpperCase()));
                if(profileService.createNewProfile(profile)){
                    status = "Success";
                    statusReg = true;
                }
                else {
                    status = "This email already exists";
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"RegisterCommand have a problem with service layer",e);
            }

        }

        request.setAttribute(MESSAGE,status);
        request.setAttribute("statusReg",statusReg);
        List<String> answer = Arrays.asList(status,String.valueOf(statusReg));
        request.setAttribute(ATTR_PAGE,JSON);
        return new Gson().toJson(answer);

    }





}
