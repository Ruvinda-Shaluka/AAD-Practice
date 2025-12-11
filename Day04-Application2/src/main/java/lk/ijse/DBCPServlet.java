package lk.ijse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/dbcp")
public class DBCPServlet extends HttpServlet {
    BasicDataSource ds;
    @Override
    public void init() throws ServletException {
        ds=new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/javaeeapp ");
        ds.setUsername("root");
        ds.setPassword("Shaluka020524");
        ds.setInitialSize(5);
        ds.setMaxTotal(5);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp2","root","12345678");
            Connection connection=ds.getConnection();
            String query="INSERT INTO customer (id,name,address) VALUES (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,address);
            int rowInserted=preparedStatement.executeUpdate();
            if(rowInserted>0){
                resp.getWriter().println("Customer Saved Successfully");
            }else {
                resp.getWriter().println("Customer Saved Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ (Get All or Get by ID)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Connection connection= ds.getConnection();
            if (id==null) {
                String query="SELECT * FROM customer";
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String cId=resultSet.getString("id");
                    String cName=resultSet.getString("name");
                    String cAddress=resultSet.getString("address");
                    resp.getWriter().println(cId+","+cName+","+cAddress);
                }
            }else  {
                String query="SELECT * FROM customer WHERE id=?";
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,id);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String cId=resultSet.getString("id");
                    String cName=resultSet.getString("name");
                    String cAddress=resultSet.getString("address");
                    resp.getWriter().println(cId+","+cName+","+cAddress);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        try {
            Connection connection=ds.getConnection();
            String query="UPDATE customer SET name=?,address=? WHERE id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,address);
            preparedStatement.setString(3,id);
            int rowInserted=preparedStatement.executeUpdate();
            if(rowInserted>0){
                resp.getWriter().println("Customer Updated Successfully");
            }else {
                resp.getWriter().println("Customer Updated Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp2",
                    "root","12345678");
            String query="delete from customer WHERE id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            int rowInserted=preparedStatement.executeUpdate();
            if(rowInserted>0){
                resp.getWriter().println("Customer Saved Successfully");
            }else {
                resp.getWriter().println("Customer Saved Failed");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}