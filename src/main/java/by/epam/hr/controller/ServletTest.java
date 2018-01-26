package by.epam.hr.controller;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/Servlet")
public class ServletTest extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/plain");
        request.setAttribute("user", "Administrator");
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("firstName");
        PrintWriter out = response.getWriter();
        try {
            Connection cn =  setDriver();


            createItem(cn,id,name);
            selectAll(cn,out);
            cn.close();

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    private void createItem(Connection cn, int id, String name) throws SQLException {
        String sqlQuery = "INSERT INTO customers(cust_id,cust_name) VALUES(?,?)";
        PreparedStatement ps = null;
        ps = cn.prepareStatement(sqlQuery);
        ps.setInt(1,id);
        ps.setString(2,name);
        ps.executeUpdate();
    }
    private void selectAll(Connection cn, PrintWriter out) throws SQLException {
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM customers;");
        while (rs.next()){
            out.append(rs.getLong("cust_id") + "\t" + rs.getString("cust_name")+"</p>" );
        }
    }
    private Connection setDriver() throws ClassNotFoundException, SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection cn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/gleb", "root", "root");
        return  cn;
    }


}
