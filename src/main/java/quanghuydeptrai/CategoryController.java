package org.example.trangdangnhap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet(name = "helloJsonServlet", urlPatterns = "/hello-json")
public class HelloJsonServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("HelloJsonServlet đã khởi tạo.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json; charset=UTF-8");

        String json = String.format(
                "{ \"message\": \"%s\", \"time\": \"%s\" }",
                "Xin chào, đây là Servlet JSON demo!",
                LocalDateTime.now()
        );

        PrintWriter writer = resp.getWriter();
        writer.write(json);
    }

    @Override
    public void destroy() {
        System.out.println("HelloJsonServlet đã bị hủy.");
    }
}
