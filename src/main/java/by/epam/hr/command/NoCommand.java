package by.epam.hr.command;


import by.epam.hr.dispatcher.PageDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        request.setAttribute(ATTR_PAGE,REDIRECT_PAGE);
        return page;
    }
}
