package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.dao.impl.OrderDAOImpl;
import ute.fit.entity.OrderEntity;
import ute.fit.service.IOrderService;
import ute.fit.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/barista/orders")
public class BaristaOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final IOrderService orderService = new OrderServiceImpl(new OrderDAOImpl());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Lấy dữ liệu thô (Object[]) từ Service
    	List<Object[]> rawData = orderService.getPendingOrdersDataToday();

    	List<Map<String, Object>> uiOrders = rawData.stream().map(row -> {
    	    Map<String, Object> map = new HashMap<>();
    	    map.put("orderID", row[0]);
    	    map.put("orderDate", row[1]);
    	    map.put("customerName", row[2]); // Đây là c.name từ database
    	    map.put("totalAmount", row[3]);
    	    return map;
    	}).collect(Collectors.toList());

        request.setAttribute("pendingOrders", uiOrders);
        request.getRequestDispatcher("/views/barista/barista-orders.jsp").forward(request, response);
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
                orderService.updateState(orderId, "COMPLETE", null);
                session.setAttribute("message", "Đơn hàng #" + orderId + " đã được pha chế xong!");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Định dạng mã đơn hàng không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống: Không thể cập nhật trạng thái đơn hàng.");
        }
        response.sendRedirect(request.getContextPath() + "/barista/orders");
    }
}