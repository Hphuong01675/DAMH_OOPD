package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ute.fit.service.IDashboardService;
import ute.fit.service.impl.DashboardServiceImpl;


import java.io.IOException;
import java.util.Map;

// 🔥 thêm Gson để convert JSON
import com.google.gson.Gson;

@WebServlet("/admin/dashboard")
public class AdminDashboardController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IDashboardService dashboardService;

    @Override
    public void init() throws ServletException {
        dashboardService = new DashboardServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. DATA (Lấy các giá trị nguyên thủy và Map từ Service)
            double totalRevenue = dashboardService.getTodayRevenue();
            long totalOrders = dashboardService.getTodayOrders();
            
            // Controller nhận Map, không phải BeverageEntity
            Map<String, Object> bestSeller = dashboardService.getBestSellerInfo();

            Map<String, Double> revenueByWeek = dashboardService.getRevenueByWeek();

            // 2. SET ATTRIBUTE
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("bestSeller", bestSeller);

            Gson gson = new Gson();
            request.setAttribute("days", gson.toJson(revenueByWeek.keySet()));
            request.setAttribute("revenues", gson.toJson(revenueByWeek.values()));

            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải dashboard!");
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
        }
    }
}