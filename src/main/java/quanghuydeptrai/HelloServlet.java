package org.example.trangdangnhap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    private static final String GREETING = "Xin chào, đây là Servlet demo!";

    @Override
    public void init() throws ServletException {
        // Có thể khởi tạo resource ở đây nếu cần
        System.out.println("HelloServlet đã khởi tạo.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("<!DOCTYPE html>")
                .append("<html><head><title>Hello Servlet</title></head><body>")
                .append("<h2>").append(GREETING).append("</h2>")
                .append("<p>Thời gian hiện tại: ")
                .append(LocalDateTime.now().toString())
                .append("</p>")
                .append("</body></html>");
    }

    @Override
    public void destroy() {
        System.out.println("HelloServlet đã bị hủy.");
    }
}
