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
            String action = request.getParameter("action");
            String idRaw = request.getParameter("orderId");

            if (idRaw != null && "COMPLETE".equals(action)) {
                Long orderId = Long.parseLong(idRaw);
                
                // 1. LẤY ID BARISTA TỪ SESSION THEO CHUẨN CỦA BẠN
                Long baristaId = null;
                
                // Lấy attribute "user" giống hệt cách bạn gọi ${sessionScope.user} trong JSP
                Object sessionObj = session.getAttribute("user"); 
                
                if (sessionObj != null) {
                    // Ép kiểu về UserDTO để lấy ID
                    ute.fit.model.UserDTO currentUser = (ute.fit.model.UserDTO) sessionObj;
                    baristaId = currentUser.getId(); 
                    
                    System.out.println(">>> Đã lấy được ID Barista từ session: " + baristaId);
                } else {
                    System.err.println(">>> Không tìm thấy 'user' trong session!");
                }

                // 2. LƯU XUỐNG DATABASE
                // Gọi tới OrderServiceImpl -> updateState -> update baristaId
                orderService.processOrder(orderId, baristaId); 
                
                session.setAttribute("message", "Đơn hàng #" + orderId + " đã hoàn thành!");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi xử lý đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/barista/orders");
    }
}