package by.epam.hr.command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/account/reg")
public class Controller extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterCommand registerCommand = new RegisterCommand();
        registerCommand.execute(req,resp);
        PrintWriter fout = resp.getWriter();
        fout.print("ЕС БЛЯЫ");
    }
}
