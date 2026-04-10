package ute.fit.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.fit.model.UserDTO;
import ute.fit.service.INotificationService;
import ute.fit.service.impl.NotificationServiceImpl;

@WebServlet("/admin/notifications")
public class AdminNotificationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final INotificationService notificationService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("selectedGroup", defaultGroup(req.getParameter("group")));
        req.getRequestDispatcher("/WEB-INF/views/admin/admin-notifications.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String subject = trim(req.getParameter("subject"));
        String body = trim(req.getParameter("body"));
        String group = defaultGroup(req.getParameter("group"));

        req.setAttribute("subject", subject);
        req.setAttribute("body", body);
        req.setAttribute("selectedGroup", group);

        if (isBlank(subject) || isBlank(body)) {
            req.setAttribute("error", "Please fill in both subject and message content.");
            req.getRequestDispatcher("/WEB-INF/views/admin/admin-notifications.jsp").forward(req, resp);
            return;
        }

        UserDTO currentUser = (UserDTO) req.getSession().getAttribute("user");

        try {
            notificationService.broadcastToRole(subject + ": " + body, group, currentUser);
            req.setAttribute("success", "Notification sent successfully to group: " + group);
            req.setAttribute("subject", "");
            req.setAttribute("body", "");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher("/WEB-INF/views/admin/admin-notifications.jsp").forward(req, resp);
    }

    private String defaultGroup(String group) {
        if ("Staff Only".equalsIgnoreCase(group)) {
            return "Staff Only";
        }
        if ("Barista Only".equalsIgnoreCase(group)) {
            return "Barista Only";
        }
        return "All Members";
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
