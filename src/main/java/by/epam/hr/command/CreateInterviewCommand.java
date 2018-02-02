package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Interview;
import by.epam.hr.model.InterviewType;
import by.epam.hr.model.Profile;
import by.epam.hr.service.InterviewService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

public class CreateInterviewCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String ID ="id";
    private static final String DATE ="date";
    private static final String TYPE ="type";
    private static final String DESCRIPTION ="description";
    private static final String REGEXP_ID = "^\\d+$";
    private static final String REGEXP_DATE = "^2\\d{3}-[01]\\d-[0-3][\\d]T[0-2]\\d:[0-5]\\d$";
    private static final String REGEXP_TYPE = "^(common|technical)$";
    private static final String REGEXP_DESCRIPTION = "^[\\w\\W\\dа-яёА-ЯЁ\\s-]{0,140}$";
    private static final String PAGE = "/controller?command=interview&id=";
    private static final String PAGE_GOOD = "/controller?command=selectInterview";
    private static final String MESSAGE = "&message=";
    private InterviewService interviewService;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile) request.getSession(false).getAttribute(PROFILE);
        request.setAttribute(ATTR_PAGE,REDIRECT_PAGE);
        StringBuilder page = new StringBuilder();
        if(profile != null && profile.getRole().name().equalsIgnoreCase("EMPLOYER")) {
            String idStr = request.getParameter(ID);
            String date = request.getParameter(DATE);
            String type = request.getParameter(TYPE);
            String desc = request.getParameter(DESCRIPTION);
            request.setAttribute(ATTR_PAGE, REDIRECT_PAGE);
            page.append(PAGE);
            if(idStr == null || !idStr.matches(REGEXP_ID)){
                return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
            }
            page.append(idStr).append(MESSAGE);

            if(!date.matches(REGEXP_DATE)){
                page.append("Incorrect Date Field");
                return page.toString();
            }
            else {
                date = date.replace('T',' ');
                date+=":00";
            }
            if(!type.matches(REGEXP_TYPE)){
                page.append("Incorrect Type Field");
                return page.toString();
            }
            if(!desc.matches(REGEXP_DESCRIPTION)){
                page.append("Incorrect Description Field");
                return page.toString();
            }
            try {
                Long id = Long.valueOf(idStr);
                interviewService = new InterviewService();
                Interview interview = new Interview();
                interview.setCandidateID(id);
                interview.setInterviewDescription(desc);
                interview.setEmployerID(profile.getProfileID());
                interview.setInterviewTime(Timestamp.valueOf(date));
                interview.setInterviewType(InterviewType.valueOf(type.toUpperCase()));
                interviewService.insertInterview(interview);
                return PAGE_GOOD;

            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"CreateInterviewCommand can not create a interview");
                page.append("Incorrect Input Data");
                return page.toString();
            }
        }
        else {
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }

    }
}
