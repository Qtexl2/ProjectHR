package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.service.ProfileService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SelectUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SelectUserCommand.class);
    private ProfileService profileService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
        if(profile != null){
            try {
                profileService = new ProfileService();
                List<Profile> profiles;
                if(profile.getRole().name().equalsIgnoreCase("ADMIN")){
                    profiles = profileService.select();
                    request.setAttribute("profiles",profiles);
                    return PageDispatcher.getInstance().getProperty(PageDispatcher.ADMIN_USER_MANAGER_PAGE);
                }
                else{
                    return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
                }

            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"SelectUserCommand can not return a list of profile");
                return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_500_PATH);
            }
        }
        else {
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
    }
}
