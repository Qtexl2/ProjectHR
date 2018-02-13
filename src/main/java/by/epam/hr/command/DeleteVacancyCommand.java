package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Vacancy;
import by.epam.hr.service.VacancyService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteVacancyCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteVacancyCommand.class);
    private static final String REGEXP_ID = "\\d+";
    private static final String PAGE = "/controller?command=myVacancies";
    private VacancyService vacancyService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")){
            String idStr = request.getParameter("vacancyId");
            request.setAttribute(ATTR_PAGE, REDIRECT_PAGE);
            if(!idStr.matches(REGEXP_ID)){
                return PAGE;
            }
            try{
                vacancyService = new VacancyService();
                vacancyService.deleteVacancy(Long.parseLong(idStr));
                LOGGER.log(Level.INFO,"Delete vacancy by employer " + profile.getEmail());

            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"DeleteVacancy can not delete a vacancy");
                return PAGE;
            }
        }
        else {
            request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        request.setAttribute(ATTR_PAGE, REDIRECT_PAGE);
        return PAGE ;
    }
}
