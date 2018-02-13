package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.service.VacancyService;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VacancyRespondCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger(VacancyRespondCommand.class);
    VacancyService vacancyService;
    public VacancyRespondCommand() {
        try {
            vacancyService = new VacancyService();
        } catch (ServiceException e) {
            LOGGER.log(Level.WARN,"Object not created",e);
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        String message = null;
        if (profile != null) {
            String id = request.getParameter("idVac");
            if (id != null){
                try {
                    vacancyService.insertVacancyAndProfile(profile.getProfileId(),Long.parseLong(id));
                    message = "Success";
                } catch (ServiceException e) {
                    LOGGER.log(Level.WARN,"VacancyRespondCommand have a problem with service layer",e);
                }
            }
        }
        else {
            request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        request.setAttribute(ATTR_PAGE, JSON);
        return new Gson().toJson(message);
    }
}
