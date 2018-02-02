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


public class VacancyCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String VACANCY_ID = "vacancyId";
    private VacancyService vacancyService;
    public VacancyCommand() { }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String strId = request.getParameter(VACANCY_ID);
        request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
        String page = null;
        if(strId != null) {
            Long id = Long.valueOf(strId);
            try {
                vacancyService = new VacancyService();

                Vacancy vacancy = vacancyService.selectVacancyById(id);
                if (vacancy != null) {
                    request.setAttribute(RequestHelper.VACANCY, vacancy);
                    page = PageDispatcher.getInstance().getProperty(PageDispatcher.VACANCY_PAGE_PATH);
                }
                else {
                    page = PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_404_PATH);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN, "VacancyCommand have a problem with service layer", e);
                return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_404_PATH);
            }
        }
        else {
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_404_PATH);
        }
        return page;
    }
}
