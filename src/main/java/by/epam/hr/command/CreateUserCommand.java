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

public class CreateUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CreateUserCommand.class);
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_ROLE = "role";
    private static final String REGEXP_ROLE = "(candidate|employer|admin)";
    private static final String REGEXP_EMAIL = ".+@.+\\..+";
    private static final String REGEXP_PASSWORD = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
    private ProfileService profileService;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String role = request.getParameter(PARAM_ROLE);
        boolean statusReg = false;
        String status = null;
        request.setAttribute(ATTR_PAGE,JSON);
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        if(profile != null && profile.getRole().name().equalsIgnoreCase("ADMIN")){
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
                    profileService = new ProfileService();
                    profile = new Profile(email,password, Role.valueOf(role.toUpperCase()));
                    if(profileService.createNewProfile(profile)){
                        status = "Success";
                        statusReg = true;
                        LOGGER.log(Level.INFO,"User was created with email" + email + " by admin " + profile.getProfileId());
                    }
                    else {
                        status = "This email already exists";
                    }
                } catch (ServiceException e) {
                    LOGGER.log(Level.WARN,"RegisterCommand have a problem with service layer",e);
                    status = "Wrong Email or password format";
                    request.setAttribute(MESSAGE,status);
                    request.setAttribute("statusReg",statusReg);
                    List<String> answer = Arrays.asList(status,String.valueOf(statusReg));
                    return new Gson().toJson(answer);
                }
            }
        }
        else {
            status = "Access error";
        }
        request.setAttribute(MESSAGE,status);
        request.setAttribute("statusReg",statusReg);
        List<String> answer = Arrays.asList(status,String.valueOf(statusReg));
        return new Gson().toJson(answer);

    }





}
