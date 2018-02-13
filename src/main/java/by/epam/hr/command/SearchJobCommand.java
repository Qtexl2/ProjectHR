package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Vacancy;
import by.epam.hr.service.VacancyService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchJobCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SearchJobCommand.class);
    private static final String JOB = "job";
    private static final String LOCATION = "location";
    private VacancyService vacancyService;
    public SearchJobCommand() {
        try {
            vacancyService = new VacancyService();
        } catch (ServiceException e) {
            LOGGER.log(Level.WARN,"Object not created",e);
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String job = request.getParameter(JOB);
        String location = request.getParameter(LOCATION);
        job = job == null?"":job;
        location = location == null?"":location;
        try {

            List<Vacancy> vacancies = vacancyService.selectVacancyByLocAndTitle(job, location);
            if(!job.equals("")) {
                String generateRegExp = "[" + job.toLowerCase() + job.toUpperCase() + "]{" + job.length() + "}";
                for (Vacancy vacancy : vacancies) {
                    String title = vacancy.getVacancyTitle();
                    Pattern pattern = Pattern.compile(generateRegExp);
                    Matcher matcher = pattern.matcher(title);
                    while (matcher.find()) {
                        String nextWord = matcher.group();
                        title = title.replace(nextWord, "<span class=\"mark-job\">" + nextWord + "</span>");
                    }
                    vacancy.setVacancyTitle(title);
                }
            }
            request.setAttribute("vacancies",vacancies);

        } catch (ServiceException e) {
            LOGGER.log(Level.WARN,"SearchJobCommand have a problem with service layer",e);
        }
        String page = PageDispatcher.getInstance().getProperty(PageDispatcher.VACANCIES_PAGE_PATH);
        request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
        return page;
    }
}
