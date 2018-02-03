package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.model.Profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateInterviewPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        request.setAttribute(ATTR_PAGE,FORWARD_PAGE);

        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")) {
            request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
            return PageDispatcher.getInstance().getProperty(PageDispatcher.CREATE_INTERVIEW_PAGE_PATH);
        }
        else {
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);

        }
    }
}
