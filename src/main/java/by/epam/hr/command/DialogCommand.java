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

public class DialogCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private ProfileService profileService;
    private static final String DIALOGS = "dialogs";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        List<Profile> profiles;
        Profile profile = (Profile) request.getSession(false).getAttribute(PROFILE);
        if(profile != null){
            Long id = profile.getProfileID();
            try {
                profileService = new ProfileService();
                profiles = profileService.selectDialogById(id);
                request.setAttribute(DIALOGS,profiles);
                request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
                page = PageDispatcher.getInstance().getProperty(PageDispatcher.DIALOG_PAGE_PATH);
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"DialogCommand can not return a list of profiles");
            }
        }
        else{
            request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        return page;
    }
}
