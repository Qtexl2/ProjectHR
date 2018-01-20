package by.epam.hr.controller;

import by.epam.hr.command.Command;
import by.epam.hr.command.RequestHelper;
import by.epam.hr.connection.ConnectionPool;
import by.epam.hr.model.Vacancy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/controller")
public class ServletController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ПОПАЛ");
        Command command = RequestHelper.getInstance().getCommand(request);
        String page = command.execute(request, response);
//        if (request.getAttribute(Command.ATTR_PAGE) == Command.REDIRECT_PAGE) {
        System.out.println(((List<Vacancy>) request.getAttribute("vacancies")));
//            response.sendRedirect(page);
//        }
//        else if (request.getAttribute(Command.ATTR_PAGE) == Command.FORWARD_PAGE) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
//
        }
}
