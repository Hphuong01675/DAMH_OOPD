package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.service.IOrderService;
import ute.fit.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/barista/orders")
public class BaristaOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. L?y d? li?u thô (Object[]) t? Service
    	List<Object[]> rawData = orderService.getPendingOrdersDataToday();

    	List<Map<String, Object>> uiOrders = rawData.stream().map(row -> {
    	    Map<String, Object> map = new HashMap<>();
    	    map.put("orderID", row[0]);
    	    map.put("orderDate", row[1]);
    	    map.put("customerName", row[2]); // Ðây là c.name t? database
    	    map.put("totalAmount", row[3]);
    	    return map;
    	}).collect(Collectors.toList());

        request.setAttribute("pendingOrders", uiOrders);
        request.getRequestDispatcher("/WEB-INF/views/barista/barista-orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        try {
            String action = request.getParameter("action");
            String idRaw = request.getParameter("orderId");

            if (idRaw != null && "COMPLETE".equals(action)) {
                Long orderId = Long.parseLong(idRaw);
                
                // CHU?N: G?i phuong th?c x? lý don hàng thay vì update chu?i
                orderService.processOrder(orderId); 
                
                session.setAttribute("message", "Ðon hàng #" + orderId + " dã pha ch? xong và ghi nh?n doanh thu!");
            }
        } catch (Exception e) {
            session.setAttribute("error", "L?i x? lý don hàng.");
        }
        response.sendRedirect(request.getContextPath() + "/barista/orders");
    }
}
