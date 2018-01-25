package by.epam.hr.command;

import by.epam.hr.dao.FactoryDAO;
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

public class AuthorizationCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String NAME_ATTRIBUTE_PROFILE = "profile";
    private static final String NAME_ATTRIBUTE_MESSAGE = "message";
    private static final String MESSAGE = "Incorrect email or password";
    private ProfileService profileService;
    public AuthorizationCommand(){
        profileService = new ProfileService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Profile profile;
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        if(password != null && email != null && password.length() < 30 && email.length() < 50){
            try {
                profile = profileService.checkEmailAndPassword(email,EncryptionPassword.encrypt(password));
                if(profile != null){

                    request.getSession().setAttribute(NAME_ATTRIBUTE_PROFILE,profile);
                    page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
                    System.out.println("Прошел авторизацию");
                    System.out.println( profile);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"AuthorizationCommand have a problem with service layer",e);

            }
        }
        if(page == null){
            request.setAttribute(NAME_ATTRIBUTE_MESSAGE,MESSAGE);
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.AUTHORIZATION_PAGE_PATH);
        }
        return page;
    }
}
