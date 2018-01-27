package by.epam.hr.controller;

import by.epam.hr.dispatcher.PageDispatcher;
import by.epam.hr.exception.ServiceException;
import by.epam.hr.model.Profile;
import by.epam.hr.service.ProfileService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/images")
public class ImageController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        Profile profile = (Profile) request.getSession().getAttribute("profile");
        if(profile != null) {
            ProfileService profileService = new ProfileService();
            Long id = profile.getProfileID();
            ServletOutputStream outputStream = null;
            try {
                byte[] out = profileService.selectPhoto(id);
                response.setContentLength(out.length);
                outputStream = response.getOutputStream();
                outputStream.write(out);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            finally {
                if(outputStream != null){
                    outputStream.close();
                }
            }
        }
        else{
            String page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }

    }
}