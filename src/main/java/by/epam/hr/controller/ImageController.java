package by.epam.hr.controller;

import by.epam.hr.command.Command;
import by.epam.hr.command.RequestHelper;
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
        ProfileService profileService = new ProfileService();
        Profile profile = (Profile) request.getSession().getAttribute("profile");
        Long id = profile.getProfileID();
        try {
            byte[] out = profileService.selectPhoto(id);
            response.setContentLength(out.length);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(out);
            outputStream.close();

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}