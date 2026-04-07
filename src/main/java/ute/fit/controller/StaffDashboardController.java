package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.model.StaffDTO;
import ute.fit.service.IOrderService;
import ute.fit.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/staff/dashboard"})
public class StaffDashboardController extends HttpServlet {
    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        // Lấy trực tiếp DTO từ session
        StaffDTO staff = (StaffDTO) session.getAttribute("user");

        if (staff == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Tầng Service và DAO sẽ tự xử lý việc dùng ID để truy vấn DB
        Map<String, Object> stats = orderService.getStaffDailyStats(staff.getId());
        
        req.setAttribute("stats", stats);
        req.setAttribute("staff", staff); 

        req.getRequestDispatcher("/WEB-INF/views/staff/dashboard.jsp").forward(req, resp);
    }
}

