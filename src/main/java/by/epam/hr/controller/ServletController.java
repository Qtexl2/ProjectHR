package by.epam.hr.controller;

import by.epam.hr.command.Command;
import by.epam.hr.command.RequestHelper;
import by.epam.hr.connection.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Class ServletController.
 */
@WebServlet("/controller")
@MultipartConfig

public class ServletController extends HttpServlet {

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
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
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
        Command command = RequestHelper.getInstance().getCommand(request);
        String result = command.execute(request, response);
        String attPage = (String) request.getAttribute(Command.ATTR_PAGE);

        switch (attPage) {
            case Command.FORWARD_PAGE:
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(result);
                dispatcher.forward(request, response);
                break;
            case Command.REDIRECT_PAGE:
                response.sendRedirect(result);
                break;
            case Command.JSON:
                PrintWriter out = response.getWriter();
                out.print(result);
                break;
        }
    }
}
