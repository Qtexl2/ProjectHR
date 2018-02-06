package by.epam.hr.command;

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

public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String ID_USERS = "ids[]";
    private static final String SUCCESS = "Success";
    private static final String PROBLEM = "Problem";
    private static final String ACCESS_ERROR = "Problem";
    private static final String REGEXP_ID = "^\\d+$";
    private ProfileService profileService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile)request.getSession().getAttribute(PROFILE);
        String message;
        request.setAttribute(ATTR_PAGE, JSON);
        if(profile != null  && Role.ADMIN.equals(profile.getRole())) {
            String[] arrayId = request.getParameterValues(ID_USERS);
            try {
                profileService = new ProfileService();
                if(arrayId != null){
                    for (String idStr: arrayId) {
                        if(idStr.matches(REGEXP_ID)){
                            profileService.delete(Long.parseLong(idStr));
                        }
                    }
                    message = SUCCESS;
                }
                message= PROBLEM;
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"DeleteUserCommand cannot delete user",e);
                message = PROBLEM;
            }

        }
        else {
            message = ACCESS_ERROR;
        }
        return new Gson().toJson(message);
        }
}
