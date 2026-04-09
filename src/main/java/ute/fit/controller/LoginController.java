package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.model.UserDTO;
import ute.fit.service.IAuthService;
import ute.fit.service.impl.AuthServiceImpl;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IAuthService authService = new AuthServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        disableCache(resp);
        req.setAttribute("selectedRole", defaultRole(req.getParameter("role")));
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
        String phone = trim(req.getParameter("phone"));
        String password = trim(req.getParameter("password"));
        String selectedRole = defaultRole(req.getParameter("role"));

        // Gọi Service và nhận về DTO
        UserDTO user = authService.login(phone, password, selectedRole);

        if (user == null) {
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            req.setAttribute("selectedRole", selectedRole);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("user", user); // Lưu DTO vào session

        // Sử dụng đúng tên phương thức resolveTarget và truyền vào String role từ DTO
        resp.sendRedirect(req.getContextPath() + resolveTarget(user.getRole()));
    }

    private String resolveTarget(String role) {
        if ("Admin".equalsIgnoreCase(role)) {
            return "/admin/dashboard";
        }
        if ("Barista".equalsIgnoreCase(role)) {
            return "/barista/dashboard";
        }
        return "/staff/dashboard";
    }

    private String defaultRole(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return "admin";
        }
        if ("barista".equalsIgnoreCase(role)) {
            return "barista";
        }
        return "staff";
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }

    private void disableCache(HttpServletResponse resp) {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
    }
}
