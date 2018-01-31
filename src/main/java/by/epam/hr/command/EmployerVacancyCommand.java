package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Vacancy;
import by.epam.hr.service.VacancyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EmployerVacancyCommand implements Command{
    private VacancyService vacancyService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        Profile profile = (Profile) request.getSession(false) .getAttribute(PROFILE);

        String page;
        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")){
            try {
                vacancyService = new VacancyService();
                List<Vacancy> vacancies = vacancyService.selectVacancyByEmployer(profile.getProfileID());
                request.setAttribute("vacancies",vacancies);
                request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
                page = PageDispatcher.getInstance().getProperty(PageDispatcher.EMPLOYER_VACANCIES_PAGE_PATH);
            }
            catch (ServiceException e) {
                request.setAttribute(ATTR_PAGE,REDIRECT_PAGE);
                page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
            }
        }
        else{
            request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        return page;
    }
}
