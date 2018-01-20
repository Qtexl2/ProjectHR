package by.epam.hr.command;

import by.epam.hr.dao.VacancyDAO;
import by.epam.hr.dao.dbmysql.VacancyMysqlDAO;
import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Vacancy;
import by.epam.hr.service.VacancyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SearchJobCommand implements Command {
    private static final String JOB = "job";
    private static final String LOCATION = "location";
    private VacancyService vacancyService;
    public SearchJobCommand() {
        vacancyService = new VacancyService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getAttribute(RequestHelper.SEARCH_JOB_PARAM);
        String job = request.getParameter(JOB);
        String location = request.getParameter(LOCATION);
        System.out.println(job + " \"   с Search\"");
        System.out.println(location + "   с Search");
        job = job == null?"":job;
        location = location == null?"":location;
        try {
            List<Vacancy> vacancies = vacancyService.selectVacancyByLocAndTitle(job, location);
            request.setAttribute("vacancies",vacancies);
            System.out.println(vacancies);

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        String page = PageDispatcher.getInstance().getProperty(PageDispatcher.VACANCY_PAGE_PATH);
        System.out.println(page + " эта стр");
        return page;
    }
}
