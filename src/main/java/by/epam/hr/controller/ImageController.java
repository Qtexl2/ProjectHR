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

/**
 * The Class ImageController.
 */
@WebServlet("/images")
public class ImageController extends HttpServlet {

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        String idStr = request.getParameter("id");
        RequestDispatcher dispatcher;
        Profile profile = (Profile) request.getSession().getAttribute("profile");
        String page;
        if(profile != null && idStr !=null) {
            Long id = Long.parseLong(idStr);
            ServletOutputStream outputStream = null;
            try {
                ProfileService profileService = new ProfileService();
                byte[] out = profileService.selectPhoto(id);
                response.setContentLength(out.length);
                outputStream = response.getOutputStream();
                outputStream.write(out);
            } catch (ServiceException e) {
                page = PageDispatcher.getInstance().getProperty(PageDispatcher.PAGE_404_PATH);
                dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
            finally {
                if(outputStream != null){
                    outputStream.close();
                }
            }
        }
        else{
            page = PageDispatcher.getInstance().getProperty(PageDispatcher.MAIN_PAGE_PATH);
            dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }

    }
}