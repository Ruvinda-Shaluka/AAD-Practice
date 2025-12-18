package lk.ijse;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/*
* MIME TYPES - multipurpose Internet Mail Extension
* ___ a MIME type (Media Type) is the content negotiation
* protocol that tells the client(server) what type of
* data server(client) is sending___
*
* ___ they are used in HTTP headers (like content-Type) to describe
* the format of data being transmitted.___
*
* Basic Structure
* =================
* type/subtype
* text/plain
* text/html
* image/png
* image/jpeg
* application/json
* application/javascript
*
*
*
*
* */
@WebServlet(urlPatterns = "/mime-types")
public class MIMETypeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/" +
                "");
        PrintWriter out=resp.getWriter();
        out.println("hjghkjk");
    }
}