package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterviewCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
        return PageDispatcher.getInstance().getProperty(PageDispatcher.EDIT_INTERVIEW_PAGE_PATH);
    }
}
