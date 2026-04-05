package ute.fit.controller;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.config.JPAUtil;
import ute.fit.entity.CustomerEntity;
import ute.fit.model.CancelledState;
import ute.fit.model.Order;
import ute.fit.model.Payment;
import ute.fit.model.PendingState;
import ute.fit.model.StatusPayment;
import ute.fit.service.IDiscountService;
import ute.fit.service.impl.DiscountServiceImpl;
import ute.fit.service.payment.PaymentProcessor;
import ute.fit.service.payment.CardPaymentProcessor;
import ute.fit.service.payment.CashPaymentProcessor;
import ute.fit.service.payment.VNPAYPaymentProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/payment", "/admin/order/confirm", "/staff/payment/confirm", "/staff/order/confirm"})
public class PaymentController extends HttpServlet {
    
    private final IDiscountService discountService = new DiscountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // [CẬP NHẬT] Lấy danh sách khuyến mãi động từ Service thay vì hardcode List.of(...)
    	List<Map<String, String>> promotions = discountService.getAllPromotions();
        req.setAttribute("promotions", promotions);

        HttpSession session = req.getSession(false);
        Order order = null;
        CustomerEntity customer = null;

        if (session != null) {
            Object maybeOrder = session.getAttribute("order");
            if (maybeOrder instanceof Order o) {
                order = o;
                customer = o.getCustomer();
            }
        }

        // Nếu chưa có customer từ session, query DB
        if (customer == null) {
            try (EntityManager em = JPAUtil.getEntityManager()) {
                List<CustomerEntity> results = em.createQuery(
                    "SELECT c FROM CustomerEntity c", CustomerEntity.class)
                    .setMaxResults(1).getResultList();
                if (!results.isEmpty()) {
                    customer = results.get(0);
                }
            } catch (Exception ignored) {
                // DB chưa kết nối được, customer đầu năm null
            }
        }

        double subtotal = (order != null) ? order.calculateTotal() : 0.0;

        req.setAttribute("customer", customer);
        req.setAttribute("orderItems", order != null ? order.getItems() : List.of());
        req.setAttribute("subtotal", subtotal);
        req.getRequestDispatcher("/WEB-INF/views/staff/payment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String promo = req.getParameter("promoStrategy"); 
        String method = req.getParameter("paymentMethod");

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order == null) order = new Order();

        // 1. Tính toán tài chính (Strategy Pattern)
        double subtotal = order.calculateTotal();
        double totalAfterDiscount = discountService.getPriceWithVoucher(subtotal, promo);
        double discountAmount = subtotal - totalAfterDiscount;
        double tax = totalAfterDiscount * 0.1;
        double finalTotal = totalAfterDiscount + tax;

        // 2. Khởi tạo Processor (Adapter & Template Method)
        PaymentProcessor processor;
        if ("vnpay".equals(method)) {
            processor = new VNPAYPaymentProcessor();
        } else if ("card".equals(method)) {
            processor = new CardPaymentProcessor("VPBANK");
        } else {
            processor = new CashPaymentProcessor();
        }

        // 3. THỰC HIỆN THANH TOÁN
        Payment paymentResult = processor.processPayment(order);
        
        // Lưu trạng thái thanh toán vào model
        order.setPaymentStatus(paymentResult.getStatusPayment());

        // 4. XỬ LÝ LUỒNG TRẠNG THÁI (State Pattern)
        order.getCurrentState().handlePayment(order, paymentResult.getStatusPayment());

        // 5. Đưa thông tin sang View (Sử dụng dữ liệu thực tế từ Order)
        req.setAttribute("orderState", order.getCurrentState().getStateName());
        req.setAttribute("promoType", promo);
        req.setAttribute("paymentMethod", method);
        req.setAttribute("subtotal", subtotal);
        req.setAttribute("discount", discountAmount);
        req.setAttribute("tax", tax);
        req.setAttribute("finalTotal", finalTotal);
        
        req.setAttribute("transactionId", paymentResult.getTransactionID());
        req.setAttribute("paymentStatus", order.getPaymentStatus().name());
        
        // Lấy tên trạng thái từ State object (PENDING hoặc CANCELLED)
        req.setAttribute("orderState", order.getCurrentState().getStateName());

        req.getRequestDispatcher("/META-INF/views/staff/success.jsp").forward(req, resp);
    }
}
