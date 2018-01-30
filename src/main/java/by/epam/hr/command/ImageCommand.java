package by.epam.hr.command;

import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.service.ProfileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageCommand implements Command {
    private ProfileService profileService;
//    public ImageCommand(){
//        profileService = new ProfileService();
//    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        Profile profile = (Profile) request.getSession().getAttribute("profile");
//        Long id = profile.getProfileID();
//        byte[] out= null;
//        try {
//            out = profileService.selectPhoto(id);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }

        return "";
    }
}
