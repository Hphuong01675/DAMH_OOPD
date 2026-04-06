package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;
import ute.fit.service.IAuthService;
import ute.fit.service.impl.AuthServiceImpl;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IAuthService authService = new AuthServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("selectedRole", defaultRole(req.getParameter("role")));
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String phone = trim(req.getParameter("phone"));
        String password = trim(req.getParameter("password"));
        String selectedRole = defaultRole(req.getParameter("role"));

        AccountEntity account = authService.login(phone, password, selectedRole);
        if (account == null) {
            req.setAttribute("error", "Invalid username, password, or role.");
            req.setAttribute("selectedRole", selectedRole);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("loggedInUser", account);
        session.setAttribute("role", account.getRole());
        session.setAttribute("displayName", account.getPerson() != null ? account.getPerson().getName() : account.getUsername());

        resp.sendRedirect(req.getContextPath() + resolveTarget(account.getRole()));
    }

    private String resolveTarget(Roles role) {
        if (role == Roles.Admin) {
            return "/admin/dashboard";
        }
        if (role == Roles.Barista) {
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
}
