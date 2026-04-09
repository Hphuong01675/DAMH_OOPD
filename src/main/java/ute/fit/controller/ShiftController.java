package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.model.Account;
import ute.fit.model.Roles;
import ute.fit.model.UserDTO;
import ute.fit.service.NotificationManager;

import java.io.IOException;

@WebServlet("/shift")
public class ShiftController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final NotificationManager notificationManager = NotificationManager.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        UserDTO user = session == null ? null : (UserDTO) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Roles role = parseRole(user.getRole());
        if (role != Roles.Staff && role != Roles.Barista) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = req.getParameter("action");
        Account observer = new Account(user.getUsername(), null, true, role, null);

        if ("start".equalsIgnoreCase(action)) {
            notificationManager.addObserver(observer);
            session.setAttribute("onShift", true);
        } else if ("end".equalsIgnoreCase(action)) {
            notificationManager.removeObserver(observer);
            session.setAttribute("onShift", false);
        }

        resp.sendRedirect(resolveRedirect(req, user));
    }

    private Roles parseRole(String rawRole) {
        return Roles.valueOf(rawRole);
    }

    private String resolveRedirect(HttpServletRequest req, UserDTO user) {
        String referer = req.getHeader("Referer");
        if (referer != null && !referer.isBlank()) {
            return referer;
        }

        if ("Barista".equalsIgnoreCase(user.getRole())) {
            return req.getContextPath() + "/barista/dashboard";
        }
        return req.getContextPath() + "/staff/dashboard";
    }
}
