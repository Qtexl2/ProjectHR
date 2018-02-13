package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;
import by.epam.hr.service.ProfileService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditUserPageCommand.class);
    private static final String USER_ID = "id";
    private static final String USER = "user";
    private ProfileService profileService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        String idStr = request.getParameter(USER_ID);
        if(profile != null && idStr != null){
            Profile profileById;
            Role role = profile.getRole();
            Long id = Long.valueOf(idStr);
            try {
                profileService = new ProfileService();
                if(Role.EMPLOYER.equals(role)){
                    profileById = profileService.selectUserById(id);
                    request.setAttribute(USER,profileById);
                    request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
                    page = PageDispatcher.getInstance().getProperty(PageDispatcher.EMPLOYER_USER_PAGE);
                }
                else if(Role.ADMIN.equals(role)){
                    profileById = profileService.selectUserById(id);
                    request.setAttribute(USER,profileById);
                    request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
                    page = PageDispatcher.getInstance().getProperty(PageDispatcher.ADMIN_USER_PAGE);

                }
                else {
                    request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
                    page = PageDispatcher.getInstance().getProperty(PageDispatcher.PROFILE_PAGE_PATH);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"EditUserPageCommand can not return a profile");
                request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
                page = PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_404_PATH);
                return page;
            }
        }
        else {
            request.setAttribute(ATTR_PAGE,REDIRECT_PAGE);
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);

        }
        return page;
    }
}
