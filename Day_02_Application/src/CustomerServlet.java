import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    private final List<Customer> customerList = new ArrayList<Customer>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        // Check if customer with same ID already exists
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                resp.getWriter().write("Customer with ID " + id + " already exists");
                return;
            }
        }

        customerList.add(new Customer(id, name, address));
        customerList.forEach((customer) -> {
            System.out.println(customer.toString());
        });

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Customer added successfully");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String id = req.getParameter("id");

        if (id != null && !id.isEmpty()) {
            // Get specific customer by ID
            for (Customer customer : customerList) {
                if (customer.getId().equals(id)) {
                    out.println(customer.toString());
                    return;
                }
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("Customer not found");
        } else {
            // Get all customers
            out.println(customerList.isEmpty() ? "No customers found" : customerList);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPut");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        if (id == null || id.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ID parameter is required for update");
            return;
        }

        // Find and update customer
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                if (name != null && !name.isEmpty()) {
                    customer.setName(name);
                }
                if (address != null && !address.isEmpty()) {
                    customer.setAddress(address);
                }
                resp.getWriter().write("Customer updated successfully");
                return;
            }
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().write("Customer not found");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doDelete");
        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ID parameter is required for deletion");
            return;
        }

        // Find and remove customer
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId().equals(id)) {
                customerList.remove(i);
                resp.getWriter().write("Customer deleted successfully");
                return;
            }
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().write("Customer not found");
    }
}