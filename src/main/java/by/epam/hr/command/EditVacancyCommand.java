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

public class EditVacancyCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditVacancyCommand.class);

    private static final String POSITION ="position";
    private static final String CITY ="city";
    private static final String ID_VAC ="id";
    private static final String COMPANY ="companyName";
    private static final String ACTUAL ="actualVac";
    private static final String DESCRIPTION ="positionDescription";
    private static final String REGEXP_POSITION = "^[\\wа-яёА-ЯЁ\\s-]{1,140}$";
    private static final String REGEXP_CITY = "^[\\wа-яёА-ЯЁ\\s-]{1,40}$";
    private static final String REGEXP_COMPANY = "^[\\wа-яёА-ЯЁ\\s-]{1,95}$";
    private static final String REGEXP_ID = "\\d+";
    private static final String REGEXP_ACTUAL = "^on$";
    private static final String REGEXP_DESCRIPTION = "^[\\w\\dа-яёА-ЯЁ\\s-:()!?;]{0,2000}$";
    private static final String PAGE= "/controller?command=vacancyEdit&vacancyId=";
    private static final String MESSAGE= "&message=";
    private VacancyService vacancyService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        StringBuilder page = new StringBuilder();

        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")){
            String position = request.getParameter(POSITION);
            String city = request.getParameter(CITY);
            String company = request.getParameter(COMPANY);
            String actual = request.getParameter(ACTUAL);
            String desc = request.getParameter(DESCRIPTION);
            String idStr = request.getParameter(ID_VAC);
            boolean statusVac = false;
            request.setAttribute(ATTR_PAGE, REDIRECT_PAGE);
            if(!idStr.matches(REGEXP_ID)){
                return page.toString();
            }
            page.append(PAGE).append(idStr).append(MESSAGE);
            if(!position.matches(REGEXP_POSITION)){
                page.append("Incorrect Position Field");
                return page.toString();
            }
            if(!city.matches(REGEXP_CITY)){
                page.append("Incorrect City Field");
                return page.toString();
            }


            if(!company.matches(REGEXP_COMPANY)){
                page.append("Incorrect Company Field");
                return page.toString();
            }
            if(actual != null){
                if(!actual.matches(REGEXP_ACTUAL)){
                    page.append("Incorrect Actual Field");
                    return page.toString();
                }
                else {
                    if(actual.equals("on")){
                        statusVac = true;
                    }
                }
            }
            if(!desc.matches(REGEXP_DESCRIPTION)){
                page.append("Incorrect Description Field");
                return page.toString();
            }
            try{
                vacancyService = new VacancyService();
                Vacancy vacancy = new Vacancy();
                vacancy.setCompany(company);
                vacancy.setVacancyTitle(position);
                vacancy.setLocation(city);
                vacancy.setVacancyDescription(desc);
                vacancy.setVacancyStatus(statusVac);
                vacancy.setVacancyId(Long.parseLong(idStr));
                vacancyService.updateVacancy(vacancy);
                LOGGER.log(Level.INFO," Vacancy update by employer " + profile.getEmail());

            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"EditVacancyCommand can not create a vacancy");
                request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
                return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_500_PATH);
            }
        }
        else {
            request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        return page.toString();
    }
}
