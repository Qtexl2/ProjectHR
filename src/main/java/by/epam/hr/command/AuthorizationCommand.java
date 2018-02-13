package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.encryption.EncryptionPassword;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.service.ProfileService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationCommand.class);
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String MESSAGE_AUTH = "Incorrect email or password";
    private ProfileService profileService;
    public AuthorizationCommand(){
        try {
            profileService = new ProfileService();
        } catch (ServiceException e) {
            LOGGER.log(Level.WARN,"Object not created",e);
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Profile profile;
        LOGGER.log(Level.INFO,"Authorization EE");
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        HttpSession checkCurrentSession = request.getSession();
        if(checkCurrentSession != null){
            checkCurrentSession.invalidate();
        }
        if(password != null && email != null && password.length() < 30 && email.length() < 50){
            try {
                profile = profileService.checkEmailAndPassword(email,EncryptionPassword.encrypt(password));
                if(profile != null){
                    request.getSession().setAttribute(PROFILE,profile);
                    request.setAttribute(ATTR_PAGE, REDIRECT_PAGE);
                    page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
                    LOGGER.log(Level.INFO,"Email "+ email + "  authorized");
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"AuthorizationCommand have a problem with service layer",e);

            }
        }
        if(page == null){
            request.setAttribute(MESSAGE,MESSAGE_AUTH);
            request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.AUTHORIZATION_PAGE_PATH);
        }
        return page;
    }
}
