package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.dao.impl.OrderDAOImpl;
import ute.fit.service.IOrderService;
import ute.fit.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/barista/dashboard")
public class BaristaDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final IOrderService orderService = new OrderServiceImpl(new OrderDAOImpl());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Object accountObj = (session != null) ? session.getAttribute("account") : null;

        if (accountObj == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = getUsernameFromSession(accountObj);

        // 1. Thống kê tổng quan
        Object[] statsData = orderService.getBaristaStatsToday(username);
        double totalRevenue = statsData[0] != null ? ((Number) statsData[0]).doubleValue() : 0.0;
        long totalOrders = statsData[1] != null ? ((Number) statsData[1]).longValue() : 0;
        long pendingOrders = statsData[2] != null ? ((Number) statsData[2]).longValue() : 0;

        // 2. Lấy dữ liệu cho Top Drinks
        List<Object[]> rawTopDrinks = orderService.getTopDrinksByBaristaToday(username);
        List<Map<String, Object>> uiTopDrinks = new ArrayList<>();
        if (rawTopDrinks != null) {
            for (Object[] row : rawTopDrinks) {
                Map<String, Object> map = new HashMap<>();
                map.put("beverageName", row[0]);
                map.put("totalQuantity", row[1] != null ? ((Number) row[1]).longValue() : 0);
                uiTopDrinks.add(map);
            }
        }

        // 3. Lấy dữ liệu cho Biểu đồ (Performance Chart)
        List<Map<String, Object>> chartData = orderService.getBaristaChartDataToday(username);

        // Đẩy toàn bộ lên View
        request.setAttribute("totalRevenue", String.format("%.2f", totalRevenue));
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("pendingOrders", pendingOrders);
        request.setAttribute("topDrinks", uiTopDrinks);
        request.setAttribute("chartData", chartData);

        request.getRequestDispatcher("/WEB-INF/views/barista/barista-dashboard.jsp").forward(request, response);
    }

    private String getUsernameFromSession(Object accountObj) {
        try {
            // Hỗ trợ cả Entity hoặc Map
            if (accountObj instanceof Map) {
                return (String) ((Map<?, ?>) accountObj).get("username");
            }
            return (String) accountObj.getClass().getMethod("getUsername").invoke(accountObj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}