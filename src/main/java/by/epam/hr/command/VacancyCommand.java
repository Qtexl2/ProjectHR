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
    public VacancyCommand() {
        vacancyService = new VacancyService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String strId = request.getParameter(VACANCY_ID);
        String page ="";
        if(strId != null) {
            Long id = Long.valueOf(strId);
            try {
                Vacancy vacancy = vacancyService.selectVacancyById(id);
                if (vacancy != null) {
                    request.setAttribute(RequestHelper.VACANCY, vacancy);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN, "VacancyCommand have a problem with service layer", e);
            }
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.VACANCY_PAGE_PATH);
        }
        return page;
    }
}
