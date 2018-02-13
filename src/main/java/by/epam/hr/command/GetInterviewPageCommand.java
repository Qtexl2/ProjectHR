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
import java.util.List;

public class GetInterviewPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetInterviewPageCommand.class);
    private InterviewService interviewService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {


        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        request.setAttribute(ATTR_PAGE, FORWARD_PAGE);
        if(profile != null){
            try {
                interviewService = new InterviewService();
                List<Interview> interviews;
                if(profile.getRole().name().equalsIgnoreCase("EMPLOYER")){
                    interviews = interviewService.selectInterviewByEmployerID(profile.getProfileId());
                    request.setAttribute("interviews",interviews);
                    return PageDispatcher.getInstance().getProperty(PageDispatcher.SELECT_EMPLOYER_INTERVIEW_PAGE);
                }
                else{
                    interviews = interviewService.selectInterviewByCandidateId(profile.getProfileId());
                    request.setAttribute("interviews",interviews);
                    return PageDispatcher.getInstance().getProperty(PageDispatcher.SELECT_INTERVIEW_PAGE);
                }

            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"GetInterviewPageCommand can not return a list of interview");
                return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_500_PATH);
            }
        }
        else {
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
    }
}
