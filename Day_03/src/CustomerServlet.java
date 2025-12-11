import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaeeapp",
                "root",
                "Shaluka020524"
        );
    }

    // CREATE (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try (Connection conn = getConnection()) {

            String sql = "INSERT INTO customer (id, name, address) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, address);

            int rows = stmt.executeUpdate();

            if (rows > 0) resp.getWriter().println("Customer created.");
            else resp.getWriter().println("Customer creation failed.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // READ (GET: ALL or ONE)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try (Connection conn = getConnection()) {

            if (id == null) {
                // GET ALL
                String sql = "SELECT * FROM customer";
                PreparedStatement stmt = conn.prepareStatement(sql);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    resp.getWriter().println(
                            rs.getString("id") + " - " +
                                    rs.getString("name") + " - " +
                                    rs.getString("address")
                    );
                }

            } else {
                // GET by ID
                String sql = "SELECT * FROM customer WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, id);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    resp.getWriter().println(
                            rs.getString("id") + " - " +
                                    rs.getString("name") + " - " +
                                    rs.getString("address")
                    );
                } else {
                    resp.getWriter().println("Customer not found.");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        if (id == null) {
            resp.getWriter().println("ID is required for update.");
            return;
        }

        try (Connection conn = getConnection()) {

            String sql = "UPDATE customer SET name = ?, address = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) resp.getWriter().println("Customer updated successfully.");
            else resp.getWriter().println("Customer not found for update.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id == null) {
            resp.getWriter().println("ID is required for deletion.");
            return;
        }

        try (Connection conn = getConnection()) {

            String sql = "DELETE FROM customer WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();

            if (rows > 0) resp.getWriter().println("Customer deleted successfully.");
            else resp.getWriter().println("Customer not found for deletion.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
