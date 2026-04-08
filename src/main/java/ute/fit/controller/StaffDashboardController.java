package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.model.UserDTO;
import ute.fit.service.IOrderService;
import ute.fit.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.Map;

@WebServlet("/staff/dashboard")
public class StaffDashboardController extends HttpServlet {
    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Logic cho Dashboard
        Map<String, Object> stats = orderService.getStaffDailyStats(user.getId());
        req.setAttribute("stats", stats);
        req.setAttribute("user", user); 

        req.getRequestDispatcher("/WEB-INF/views/staff/dashboard.jsp").forward(req, resp);
    }
}