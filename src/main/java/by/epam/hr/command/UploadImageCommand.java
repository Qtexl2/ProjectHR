package by.epam.hr.command;

import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.service.ProfileService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class UploadImageCommand implements Command {
    private ProfileService profileService;
    private static final int MAX_FILE_SIZE = 4_000_000;
    private static final String ATTR_IMG = "img";
    public UploadImageCommand() {
        profileService = new ProfileService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String message = null;
        Profile profile = (Profile) request.getSession().getAttribute("profile");
        if(profile != null){
            try {
                Part part = request.getPart(ATTR_IMG);
                if(part == null){
                    message = "File is null";
                }
                else if(part.getSize() < MAX_FILE_SIZE) {
                    try (InputStream is = part.getInputStream()) {
                        profileService.updatePhoto(profile.getProfileID(), is);

                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    message = "File too large! Max 4MB";
                }
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }
        else {
            message = "Session is over";
        }

        return message;
    }
}
