package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Gender;
import by.epam.hr.model.Profile;
import by.epam.hr.model.Role;
import by.epam.hr.service.ProfileService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserAdmCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String PARAM_NAME_FIRSTNAME = "firstName";
    private static final String PARAM_NAME_LASTNAME = "lastName";
    private static final String PARAM_NAME_PHONE = "phone";
    private static final String PARAM_NAME_AGE = "age";
    private static final String PARAM_NAME_GENDER = "sex";
    private static final String PARAM_NAME_POSITION = "position";
    private static final String PARAM_NAME_COMPANY = "company";
    private static final String PARAM_NAME_DESCRIBE = "describe";
    private static final String ID_USER = "id";
    private static final String REGEXP_ID = "^\\d+$";
    private static final String REGEXP_NAME = "^[\\wа-яёА-ЯЁ\\s-]{0,45}$";
    private static final String REGEXP_PHONE = "^\\+?[\\d  -\\.]{0,18}$";
    private static final String REGEXP_AGE = "^\\d{1,3}$";
    private static final String REGEXP_GENDER = "^(male|female)$";
    private static final String REGEXP_POSITION = "^[\\w\\dа-яёА-ЯЁ\\s-]{0,99}$";
    private static final String REGEXP_COMPANY = "^[\\w\\dа-яёА-ЯЁ\\s-]{0,99}$";
    private static final String REGEXP_DESCRIBE = "^[\\w\\W\\dа-яёА-ЯЁ\\s-]{0,500}$";
    private static final String PROFILE_PAGE = "/controller?command=page&id=";
    private static final String MESSAGE = "&message=";
    private static final String MESSAGE_STATUS = "&messageStatus=";
    private ProfileService profileService;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile)request.getSession().getAttribute(PROFILE);
        StringBuilder page = new StringBuilder();
        if(profile != null  && Role.ADMIN.equals(profile.getRole())) {

            page.append(PROFILE_PAGE);
            String idStr = request.getParameter(ID_USER);
            String firstName = request.getParameter(PARAM_NAME_FIRSTNAME);
            String lastName = request.getParameter(PARAM_NAME_LASTNAME);
            String phone = request.getParameter(PARAM_NAME_PHONE);
            String ageStr = request.getParameter(PARAM_NAME_AGE);
            String genderStr = request.getParameter(PARAM_NAME_GENDER);
            String position = request.getParameter(PARAM_NAME_POSITION);
            String company = request.getParameter(PARAM_NAME_COMPANY);
            String describe = request.getParameter(PARAM_NAME_DESCRIBE);
            Integer age;
            Gender gender;
            try {
                if (idStr != null) {
                    if (!idStr.matches(REGEXP_ID)) {
                        request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
                        return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_500_PATH);
                    }
                }
                else {
                    request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
                    return PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_500_PATH);
                }

                page.append(idStr).append(MESSAGE);
                request.setAttribute(ATTR_PAGE,REDIRECT_PAGE);

                if(!firstName.matches(REGEXP_NAME)){
                    page.append("Incorrect First Name").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }

                if(!lastName.matches(REGEXP_NAME)){
                    page.append("Incorrect Last Name").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }

                if(!phone.matches(REGEXP_PHONE)){
                    page.append("Incorrect Phone number").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }

                if(!ageStr.matches(REGEXP_AGE)){
                    page.append("Incorrect Age").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }
                else {
                    age = Integer.parseInt(ageStr);
                }

                if(!genderStr.matches(REGEXP_GENDER)){
                    page.append("Incorrect Gender").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }
                else {
                    gender = Gender.valueOf(genderStr.toUpperCase());
                }

                if(!position.matches(REGEXP_POSITION)){
                    page.append("Incorrect Position").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }

                if(!company.matches(REGEXP_COMPANY)){
                    page.append("Incorrect Company").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }

                if(!describe.matches(REGEXP_DESCRIBE)){
                    page.append("Incorrect Describe").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }
                Profile userProfile = new Profile();
                userProfile.setFirstName(firstName);
                userProfile.setLastName(lastName);
                userProfile.setCompany(company);
                userProfile.setAge(age);
                userProfile.setCurrentPosition(position);
                userProfile.setDescribe(describe);
                userProfile.setGender(gender);
                userProfile.setPhone(phone);


                userProfile.setProfileID(Long.parseLong(idStr));
                profileService = new ProfileService();
                if(!profileService.updateBaseProfile(userProfile)){
                    page.append("Incorrect Describe").append(MESSAGE_STATUS).append(true);
                    return page.toString();
                }
                else{
                    page.append("Success").append(MESSAGE_STATUS).append(false);

                }
            } catch (ServiceException e) {
                page.append("Incorrect Describe").append(MESSAGE_STATUS).append(true);
                return page.toString();
            }
        }
        else{
            request.setAttribute(ATTR_PAGE,FORWARD_PAGE);
            return PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        return page.toString();
    }
}
