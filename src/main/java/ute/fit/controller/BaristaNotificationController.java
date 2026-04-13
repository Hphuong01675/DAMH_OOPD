package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.model.Notification;
import ute.fit.model.UserDTO;
import ute.fit.service.INotificationService;
import ute.fit.service.impl.NotificationServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/barista/notifications")
public class BaristaNotificationController extends HttpServlet {
    private final INotificationService notificationService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Notification> notifications = notificationService.getNotificationsForUser(user.getUsername());
        req.setAttribute("notifications", notifications);
        
        req.getRequestDispatcher("/WEB-INF/views/barista/barista-notification.jsp").forward(req, resp);
    }
}
