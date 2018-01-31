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
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String REGEXP_ID = "\\d+";

    private static final String POSITION ="position";
    private static final String CITY ="city";
    private static final String COMPANY ="companyName";
    private static final String ACTUAL ="actualVac";
    private static final String DESCRIPTION ="positionDescription";
    private static final String REGEXP_POSITION = "^[\\wа-яёА-ЯЁ\\s-]{1,140}$";
    private static final String REGEXP_CITY = "^[\\wа-яёА-ЯЁ\\s-]{1,40}$";
    private static final String REGEXP_COMPANY = "^[\\wа-яёА-ЯЁ\\s-]{1,95}$";
    private static final String REGEXP_ACTUAL = "^on$";
    private static final String REGEXP_DESCRIPTION = "^[\\w\\W\\dа-яёА-ЯЁ\\s-]{0,2000}$";
    private static final String PAGE = "/controller?command=myVacancies";
    private VacancyService vacancyService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession(false).getAttribute(PROFILE);
        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")){
            String idStr = request.getParameter("vacancyId");
            request.setAttribute(ATTR_PAGE, REDIRECT_PAGE);
            if(!idStr.matches(REGEXP_ID)){
                return PAGE;
            }
            try{
                vacancyService = new VacancyService();
                vacancyService.deleteVacancy(Long.parseLong(idStr));

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
