package org.example.trangdangnhap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.trangdangnhap.model.User;
import org.example.trangdangnhap.service.UserService;
import org.example.trangdangnhap.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet(name = "userController",
        urlPatterns = {"/login", "/register", "/logout", "/home", "/forgot-password", "/change-password"})
public class UserController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    // ================== GET ==================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/login".equals(path)) {
            showLoginPage(req, resp);
        } else if ("/register".equals(path)) {
            req.getRequestDispatcher("/Views/register.jsp").forward(req, resp);
        } else if ("/logout".equals(path)) {
            handleLogout(req, resp);
        } else if ("/home".equals(path)) {
            if (!requireLoginPage(req, resp)) return;
            req.getRequestDispatcher("/Views/home.jsp").forward(req, resp);
        } else if ("/forgot-password".equals(path)) {
            req.getRequestDispatcher("/Views/forgot-password.jsp").forward(req, resp);
        } else if ("/change-password".equals(path)) {
            if (!requireLoginPage(req, resp)) return;
            req.getRequestDispatcher("/Views/change-password.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // ================== POST ==================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/login".equals(path)) {
            doLogin(req, resp);
        } else if ("/register".equals(path)) {
            doRegister(req, resp);
        } else if ("/forgot-password".equals(path)) {
            doForgotPassword(req, resp);
        } else if ("/change-password".equals(path)) {
            doChangePassword(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    // ================== HANDLERS ==================

    /** Trang login: tự động đăng nhập nếu có session/cookie */
    private void showLoginPage(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        User userFromCookie = getUserFromRememberCookie(req);
        if (userFromCookie != null) {
            req.getSession(true).setAttribute("currentUser", userFromCookie);
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        req.getRequestDispatcher("/Views/login.jsp").forward(req, resp);
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        clearRememberCookie(req, resp);
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private boolean requireLoginPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    // ================== BUSINESS ==================

    private void doRegister(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User(username, password, email);
        if (userService.register(user)) {
            req.setAttribute("message", "Đăng ký thành công. Mời đăng nhập.");
            req.getRequestDispatcher("/Views/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại.");
            req.getRequestDispatcher("/Views/register.jsp").forward(req, resp);
        }
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean remember = "true".equals(req.getParameter("remember"));

        User user = userService.login(username, password);
        if (user != null) {
            req.getSession(true).setAttribute("currentUser", user);
            if (remember) {
                setRememberCookie(resp, user);
            }
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            req.getRequestDispatcher("/Views/login.jsp").forward(req, resp);
        }
    }

    private void doForgotPassword(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String email = req.getParameter("email");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (email == null || email.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập email");
            req.getRequestDispatcher("/Views/forgot-password.jsp").forward(req, resp);
            return;
        }
        if (newPassword == null || newPassword.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập mật khẩu mới");
            req.getRequestDispatcher("/Views/forgot-password.jsp").forward(req, resp);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp");
            req.getRequestDispatcher("/Views/forgot-password.jsp").forward(req, resp);
            return;
        }

        if (userService.forgotPassword(email, newPassword)) {
            req.setAttribute("message", "Đổi mật khẩu thành công, mời đăng nhập.");
            req.getRequestDispatcher("/Views/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Email không tồn tại hoặc có lỗi xảy ra");
            req.getRequestDispatcher("/Views/forgot-password.jsp").forward(req, resp);
        }
    }

    private void doChangePassword(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (currentPassword == null || currentPassword.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập mật khẩu hiện tại");
            req.getRequestDispatcher("/Views/change-password.jsp").forward(req, resp);
            return;
        }
        if (newPassword == null || newPassword.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập mật khẩu mới");
            req.getRequestDispatcher("/Views/change-password.jsp").forward(req, resp);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp");
            req.getRequestDispatcher("/Views/change-password.jsp").forward(req, resp);
            return;
        }

        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (userService.changePassword(currentUser.getId(), currentPassword, newPassword)) {
            req.setAttribute("message", "Đổi mật khẩu thành công");
        } else {
            req.setAttribute("error", "Mật khẩu hiện tại không đúng hoặc có lỗi xảy ra");
        }
        req.getRequestDispatcher("/Views/change-password.jsp").forward(req, resp);
    }

    // ================== COOKIE UTILS ==================

    private void setRememberCookie(HttpServletResponse resp, User user) {
        Cookie cookie = new Cookie("remember_username", user.getUsername());
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    private void clearRememberCookie(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getCookies() == null) return;
        for (Cookie cookie : req.getCookies()) {
            if ("remember_username".equals(cookie.getName())) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                resp.addCookie(cookie);
            }
        }
    }

    private User getUserFromRememberCookie(HttpServletRequest req) {
        if (req.getCookies() == null) return null;
        for (Cookie cookie : req.getCookies()) {
            if ("remember_username".equals(cookie.getName())) {
                User u = new User();
                u.setUsername(cookie.getValue());
                u.setEmail("");
                return u;
            }
        }
        return null;
    }
}
