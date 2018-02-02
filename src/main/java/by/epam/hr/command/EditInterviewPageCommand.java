package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Interview;
import by.epam.hr.model.Profile;
import by.epam.hr.service.InterviewService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

public class EditInterviewPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String INTERVIEW_ID = "id";
    private static final String REGEXP_ID ="\\d+";
    private InterviewService interviewService;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")) {
            String interviewId = request.getParameter(INTERVIEW_ID);
            Long id;
            try{
                if(interviewId.matches(REGEXP_ID)){
                    id = Long.valueOf(interviewId);
                    interviewService = new InterviewService();
                    Interview interview = interviewService.selectInterview(id);
                    request.setAttribute("interview",interview);

                    return PageDispatcher.getInstance().getProperty(PageDispatcher.EDIT_INTERVIEW_PAGE_PATH);
                }
                else throw new ServiceException("Invalid ID");
            }
            catch (ServiceException e ){
                LOGGER.log(Level.WARN,"EditInterview can not select a vacancy");
                return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_500_PATH);
            }


        }
        else {
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
    }
}
