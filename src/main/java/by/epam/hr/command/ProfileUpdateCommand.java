package by.epam.hr.command;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Gender;
import by.epam.hr.model.Profile;
import by.epam.hr.service.ProfileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileUpdateCommand implements Command {
    private static final String MESSAGE = "message";
    private static final String MESSAGE_STATUS = "messageStatus";
    private static final String PARAM_NAME_FIRSTNAME = "firstName";
    private static final String PARAM_NAME_LASTNAME = "lastName";
    private static final String PARAM_NAME_PHONE = "phone";
    private static final String PARAM_NAME_AGE = "age";
    private static final String PARAM_NAME_GENDER = "sex";
    private static final String PARAM_NAME_POSITION = "position";
    private static final String PARAM_NAME_COMPANY = "company";
    private static final String PARAM_NAME_DESCRIBE = "describe";
    private static final String REGEXP_NAME = "^[\\wа-яёА-ЯЁ\\s-]{0,45}$";
    private static final String REGEXP_PHONE = "^\\+?[\\d  -\\.]{0,18}$";
    private static final String REGEXP_AGE = "^\\d{1,3}$";
    private static final String REGEXP_GENDER = "^(male|female)$";
    private static final String REGEXP_POSITION = "^[\\w\\dа-яёА-ЯЁ\\s-]{0,99}$";
    private static final String REGEXP_COMPANY = "^[\\w\\dа-яёА-ЯЁ\\s-]{0,99}$";
    private static final String REGEXP_DESCRIBE = "^[\\w\\W\\dа-яёА-ЯЁ\\s-]{0,500}$";
    private ProfileService profileService;

    public ProfileUpdateCommand(){
        profileService = new ProfileService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Profile profile = (Profile)request.getSession().getAttribute("profile");
        String page;
        String message;
        if(profile != null){
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.PROFILE_PAGE_PATH);

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
            if(firstName != null){
                if(!firstName.matches(REGEXP_NAME)){
                    message = "Incorrect First Name";
                    request.setAttribute(MESSAGE,message);
                    request.setAttribute(MESSAGE_STATUS,true);
                    return page;
                }

            }
            else{
                firstName = profile.getFirstName();
            }

            if(lastName != null){
                if(!lastName.matches(REGEXP_NAME)){
                    message = "Incorrect Last Name";
                    request.setAttribute(MESSAGE,message);
                    request.setAttribute(MESSAGE_STATUS,true);
                    return page;
                }
            }
            else{
                lastName = profile.getLastName();
            }

            if(phone != null){
                if(!phone.matches(REGEXP_PHONE)){
                    message = "Incorrect Phone number";
                    request.setAttribute(MESSAGE_STATUS,true);
                    request.setAttribute(MESSAGE,message);
                    return page;
                }
            }
            else{
                phone = profile.getPhone();
            }

            if(ageStr != null){
                if(!ageStr.matches(REGEXP_AGE)){
                    message = "Incorrect Age";
                    request.setAttribute(MESSAGE,message);
                    request.setAttribute(MESSAGE_STATUS,true);

                    return page;
                }
                else {
                    age = Integer.parseInt(ageStr);
                }
            }
            else{
                age = profile.getAge();
            }

            if(genderStr != null){
                if(!genderStr.matches(REGEXP_GENDER)){
                    message = "Incorrect Gender";
                    request.setAttribute(MESSAGE_STATUS,true);
                    request.setAttribute(MESSAGE,message);
                    return page;
                }
                else {
                    gender = Gender.valueOf(genderStr.toUpperCase());
                }
            }
            else{
                gender = profile.getGender();
            }

            if(position != null){
                if(!position.matches(REGEXP_POSITION)){
                    message = "Incorrect Position";
                    request.setAttribute(MESSAGE_STATUS,true);
                    request.setAttribute(MESSAGE,message);
                    return page;
                }
            }
            else{
                position = profile.getCurrentPosition();
            }

            if(company != null){
                if(!company.matches(REGEXP_COMPANY)){
                    message = "Incorrect Company";
                    request.setAttribute(MESSAGE_STATUS,true);
                    request.setAttribute(MESSAGE,message);
                    return page;
                }
            }
            else{
                company = profile.getCompany();
            }

            if(describe != null){
                if(!describe.matches(REGEXP_DESCRIBE)){
                    message = "Incorrect Describe";
                    request.setAttribute(MESSAGE_STATUS,true);
                    request.setAttribute(MESSAGE,message);
                    return page;
                }
            }
            else{
                describe = profile.getDescribe();
            }
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            profile.setCompany(company);
            profile.setAge(age);
            profile.setCurrentPosition(position);
            profile.setDescribe(describe);
            profile.setGender(gender);
            profile.setPhone(phone);
            try {
                if(!profileService.updateBaseProfile(profile)){
                    message = "Incorrect data";
                    request.setAttribute(MESSAGE,message);
                    request.setAttribute(MESSAGE_STATUS,true);

                }
                else{
                    message = "Success";
                    request.setAttribute(MESSAGE,message);
                    request.setAttribute(MESSAGE_STATUS,false);

                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }

        }
        else{
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
        }
        return page;
    }
}
