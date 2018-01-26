package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.model.Profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToProfileCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile)request.getSession().getAttribute("profile");
        String page;
        if(profile == null){
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        else {
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.PROFILE_PAGE_PATH);
        }
        return page;
    }
}
