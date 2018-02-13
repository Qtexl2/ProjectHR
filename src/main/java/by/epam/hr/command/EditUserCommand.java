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
    private static final Logger LOGGER = LogManager.getLogger(EditUserCommand.class);
    private static final String LEVEL_EN = "level";
    private static final String PRE_INTERVIEW = "preInterview";
    private static final String TECH_INTERVIEW = "technicalInterview";
    private static final String ID_USER = "id";
    private static final String REGEXP_LEVEL = "^(A1|A2|B1|B2|C1|C2)$";
    private static final String REGEXP_PRE = "^[\\w\\W\\dа-яёА-ЯЁ\\s-]{0,500}$";
    private static final String REGEXP_TECH = "^[\\w\\W\\dа-яёА-ЯЁ\\s-]{0,500}$";
    private static final String REGEXP_ID = "^\\d+$";
    private static final String MESSAGE_STATUS = "&messageStatus=";
    private static final String MESSAGE = "&message=";
    private static final String USER_PAGE = "/controller?command=page&id=";

    private ProfileService profileService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder page = new StringBuilder();
        Profile profile = (Profile) request.getSession().getAttribute(PROFILE);
        String idStr = request.getParameter(ID_USER);
        String levelEn = request.getParameter(LEVEL_EN);
        String preInterview = request.getParameter(PRE_INTERVIEW);
        String technicalInterview = request.getParameter(TECH_INTERVIEW);


        if(profile != null && (Role.EMPLOYER.equals(profile.getRole()) || Role.ADMIN.equals(profile.getRole()))) {
            request.setAttribute(ATTR_PAGE,REDIRECT_PAGE);
            try{
                if (idStr != null) {
                    if (!idStr.matches(REGEXP_ID)) {
                        throw new ServiceException("User not found");
                    }
                }
                else {
                    throw new ServiceException("User not found");
                }

                Long id = Long.valueOf(idStr);
                page.append(USER_PAGE).append(idStr).append(MESSAGE);
                if (!levelEn.matches(REGEXP_LEVEL)) {
                    page.append("Incorrect English Level").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }

                if (!preInterview.matches(REGEXP_PRE)) {
                     page.append("Incorrect PreInterview field").append(MESSAGE_STATUS).append(true);
                     return page.toString();
                }

                if (!technicalInterview.matches(REGEXP_TECH)) {
                    page.append("Incorrect TechInterview field").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }

                profileService = new ProfileService();
                if(!profileService.updateAfterInterview(id,levelEn,preInterview,technicalInterview)){
                    page.append("Incorrect data").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }
                else {
                    LOGGER.log(Level.INFO,"Employer "+ profile.getEmail() + " update profile with id=" + id);

                    page.append("Success").append(MESSAGE_STATUS).append(false);
                }

            }

            catch (ServiceException e){
                LOGGER.log(Level.WARN,"EditUserCommand can not update a profile");
                request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
                return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_404_PATH);
            }
        }
        else {
            request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        return page.toString();
    }

}
