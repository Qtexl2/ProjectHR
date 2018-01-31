package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.EnglishLevel;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;
import by.epam.hr.service.ProfileService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String LEVEL_EN = "level";
    private static final String PRE_INTERVIEW = "preInterview";
    private static final String TECH_INTERVIEW = "technicalInterview";
    private static final String ID_USER = "id";
    private static final String REGEXP_LEVEL = "^(A1|A2|B1|B2|C1|C2)$";
    private static final String REGEXP_PRE = "^[\\w\\W\\dа-яёА-ЯЁ\\s-]{0,500}$";
    private static final String REGEXP_TECH = "^[\\w\\W\\dа-яёА-ЯЁ\\s-]{0,500}$";
    private static final String REGEXP_ID = "^\\d+$";
    private static final String MESSAGE_STATUS = "messageStatus";
    private static final String USER_PAGE = "/controller?command=page&id=";

    private ProfileService profileService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        String message;

        Profile profile = (Profile) request.getSession(false).getAttribute(PROFILE);
        String idStr = request.getParameter(ID_USER);
        String levelEn = request.getParameter(LEVEL_EN);
        String preInterview = request.getParameter(PRE_INTERVIEW);
        String technicalInterview = request.getParameter(TECH_INTERVIEW);

        if(profile != null && (Role.EMPLOYER.equals(profile.getRole()) || Role.ADMIN.equals(profile.getRole()))) {
            request.setAttribute(ATTR_PAGE,REDIRECT_PAGE);
            if (idStr != null) {
                if (!idStr.matches(REGEXP_ID)) {
                    message = "Incorrect ID";
                    request.setAttribute(MESSAGE, message);
                    request.setAttribute(MESSAGE_STATUS, true);
                    page = PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_404_PATH);
                    return page;
                }
            }
            else {
                message = "Incorrect ID";
                request.setAttribute(MESSAGE, message);
                request.setAttribute(MESSAGE_STATUS, true);
                page = PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_404_PATH);
                return page;
            }

            Long id = Long.valueOf(idStr);
            page = USER_PAGE+idStr;
            if (levelEn != null) {
                if (!levelEn.matches(REGEXP_LEVEL)) {
                    message = "Incorrect English Level";
                    request.setAttribute(MESSAGE, message);
                    request.setAttribute(MESSAGE_STATUS, true);
                    return page;
                }
            }
            else {
                levelEn = "A1";
            }

            if (preInterview != null) {
                if (!preInterview.matches(REGEXP_PRE)) {
                    message = "Incorrect PreInterview field";
                    request.setAttribute(MESSAGE, message);
                    request.setAttribute(MESSAGE_STATUS, true);
                    return page;
                }
            }
            else {
                preInterview ="";
            }
            if (technicalInterview != null) {
                if (!technicalInterview.matches(REGEXP_TECH)) {
                    message = "Incorrect TechInterview field";
                    request.setAttribute(MESSAGE, message);
                    request.setAttribute(MESSAGE_STATUS, true);
                    return page;
                }
            }
            else {
                technicalInterview ="";
            }
            try {
                profileService = new ProfileService();
                if(!profileService.updateAfterInterview(id,levelEn,preInterview,technicalInterview)){
                    message = "Incorrect data";
                    request.setAttribute(MESSAGE,message);
                    request.setAttribute(MESSAGE_STATUS,true);
                }
                else {
                    message = "Success";
                    request.setAttribute(MESSAGE,message);
                    request.setAttribute(MESSAGE_STATUS,false);

                }
            } catch (ServiceException e) {
                LOGGER.log(Level.WARN,"EditUserCommand can not update a profile");
            }
        }
        else {
            request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);

        }
        return page;
    }

}
