package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.model.Profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedirectToProfileCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request);
        System.out.println(request.getSession());
        System.out.println(request.getSession(false).getAttribute(PROFILE));

        Object session = request.getSession(false).getAttribute(PROFILE);
        String page;
        if(session == null){
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        else {
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.PROFILE_PAGE_PATH);
        }
        request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
        return page;
    }
}
