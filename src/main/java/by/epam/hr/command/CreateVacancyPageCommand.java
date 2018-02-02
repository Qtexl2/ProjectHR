package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.model.Profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateVacancyPageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        String page;
        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")){
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.CREATE_VACANCY_PAGE);
        }
        else {
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
        return page;
    }
}
