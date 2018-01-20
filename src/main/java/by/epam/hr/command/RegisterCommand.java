package by.epam.hr.command;

import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;
import by.epam.hr.service.ProfileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";

    private ProfileService pServ;

    public RegisterCommand(){
        pServ = new ProfileService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        Profile profile = new Profile(email,password, Role.CANDIDATE);
        try {
            pServ.createNewProfile(profile);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "GOOOODD";
    }
}
