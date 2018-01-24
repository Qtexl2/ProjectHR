package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToAuthCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return PageDispatcher.getInstance().getProperty(PageDispatcher.AUTHORIZATION_PAGE_PATH);

    }
}
