package by.epam.hr.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String ATTR_PAGE = "result";
    String FORWARD_PAGE = "forward";
    String REDIRECT_PAGE = "redirect";
    String JSON = "json";
    String MESSAGE = "message";
    String PROFILE = "profile";
    String SUCCESS = "success";

    String execute(HttpServletRequest request, HttpServletResponse response);
}
