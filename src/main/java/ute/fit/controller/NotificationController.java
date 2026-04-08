package ute.fit.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ute.fit.model.NotificationDTO;
import ute.fit.model.UserDTO;
import ute.fit.service.NotificationService;
import ute.fit.service.impl.NotificationServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/notifications")
public class NotificationController extends HttpServlet {

    private NotificationService notificationService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	
        HttpSession session = request.getSession();

        // ✅ LẤY ĐÚNG USER
        UserDTO user = (UserDTO) session.getAttribute("user");

        System.out.println("SESSION USER: " + user);

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        List<NotificationDTO> list =
                notificationService.getByUser(user.getPhone());

        request.setAttribute("notifications", list);

        request.getRequestDispatcher("/WEB-INF/views/staff/notification.jsp")
               .forward(request, response);
    }
}