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

public class EditVacancyPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String VACANCY_ID = "vacancyId";
    private static final String REGEXP_ID ="\\d+";
    private VacancyService vacancyService;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession(false).getAttribute(PROFILE);
        request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")) {
            String vacancyId = request.getParameter(VACANCY_ID);
            Long id;
            try{
                if(vacancyId.matches(REGEXP_ID)){
                    id = Long.valueOf(vacancyId);
                    vacancyService = new VacancyService();
                    Vacancy vacancy = vacancyService.selectVacancyById(id);
                    request.setAttribute("vacancy",vacancy);
                    return PageDispatcher.getInstance().getProperty(PageDispatcher.UPDATE_VACANCY_PAGE);
                }
                else throw new ServiceException("Invalid ID");
            }
            catch (ServiceException e ){
                LOGGER.log(Level.WARN,"EditVacancy can not select a vacancy");
                return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_500_PATH);
            }


        }
        else {
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
    }
}
