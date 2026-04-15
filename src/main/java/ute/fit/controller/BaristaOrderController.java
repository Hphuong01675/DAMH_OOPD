package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.entity.OrderEntity;
import ute.fit.service.IOrderService;
import ute.fit.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/barista/orders")
public class BaristaOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Trả về List<OrderEntity> thay vì dữ liệu thô (Object[]) để JSP gọi được order.items
        List<OrderEntity> uiOrders = orderService.getPendingAndPaidOrdersToday();

        request.setAttribute("pendingOrders", uiOrders);
        request.getRequestDispatcher("/WEB-INF/views/barista/barista-orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        try {
            // Lấy các tham số từ Form gửi lên
            String action = request.getParameter("action");
            String idRaw = request.getParameter("orderId");
            String reason = request.getParameter("reason"); // Thêm dòng này để lấy lý do hủy

            if (idRaw != null && action != null) {
                Long orderId = Long.parseLong(idRaw);
                
                // 1. LẤY ID BARISTA TỪ SESSION
                Long baristaId = null;
                Object sessionObj = session.getAttribute("user"); 
                
                if (sessionObj != null) {
                    ute.fit.model.UserDTO currentUser = (ute.fit.model.UserDTO) sessionObj;
                    baristaId = currentUser.getId(); 
                } else {
                    System.err.println(">>> Không tìm thấy 'user' trong session!");
                }

                // 2. GỌI SERVICE ĐỂ XỬ LÝ THEO STATE PATTERN
                // Dùng updateState thay vì processOrder để hỗ trợ đa dạng action (COMPLETE, CANCEL)
                if ("COMPLETE".equalsIgnoreCase(action) || "CANCEL".equalsIgnoreCase(action)) {
                    orderService.updateState(orderId, action, reason, baristaId);
                    
                    // Set thông báo trả về giao diện tùy theo hành động
                    if ("COMPLETE".equalsIgnoreCase(action)) {
                        session.setAttribute("message", "Đơn hàng #" + orderId + " đã hoàn thành!");
                    } else {
                        session.setAttribute("message", "Đơn hàng #" + orderId + " đã được hủy thành công!");
                    }
                }
            }
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi xử lý đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Load lại trang danh sách đơn
        response.sendRedirect(request.getContextPath() + "/barista/orders");
    }
}