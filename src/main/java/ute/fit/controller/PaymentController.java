package ute.fit.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.config.JPAUtil;
import ute.fit.entity.CustomerEntity;
import ute.fit.model.Order;
import ute.fit.model.Payment;
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
        
        // Lấy danh sách khuyến mãi động từ Database
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

        // Nếu chưa có customer từ session, query DB để lấy thông tin khách hàng mẫu (hoặc theo ID)
        if (customer == null) {
            try (EntityManager em = JPAUtil.getEntityManager()) {
                List<CustomerEntity> results = em.createQuery(
                    "SELECT c FROM CustomerEntity c", CustomerEntity.class)
                    .setMaxResults(1).getResultList();
                if (!results.isEmpty()) {
                    customer = results.get(0);
                }
            } catch (Exception ignored) {}
        }

        double subtotal = (order != null) ? order.calculateTotal() : 0.0;

        // Truyền dữ liệu xuống JSP [cite: 56, 60, 63]
        req.setAttribute("customer", customer); 
        req.setAttribute("orderItems", order != null ? order.getItems() : List.of());
        req.setAttribute("subtotal", subtotal);
        
        // Gửi ID đơn hàng để tạo mã QR nếu cần [cite: 80]
        req.setAttribute("orderId", (order != null) ? "12345" : "TEMP"); 

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
        
        // Đảm bảo số tiền giảm giá không vượt quá tổng tiền và không âm 
        double discountAmount = Math.max(0, subtotal - totalAfterDiscount);
        double tax = totalAfterDiscount * 0.1;
        double finalTotal = totalAfterDiscount + tax;

        // 2. Khởi tạo Processor (Adapter & Template Method) [cite: 100, 104, 108]
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
        
        // 4. XỬ LÝ TRỪ ĐIỂM (Nếu thanh toán thành công và dùng mã PointRedeem)
        if (paymentResult.getStatusPayment() == StatusPayment.SUCCESS) {
            CustomerEntity customer = order.getCustomer();
            // Chỉ thực hiện nếu có khách hàng và có chọn mã giảm giá [cite: 71, 74]
            if (customer != null && promo != null && !promo.equals("NONE")) {
                try {
                    // Gọi service thực hiện trừ 30 điểm trong DB 
                    discountService.canApplyPointPromotion(customer, promo);
                    
                    // Cập nhật lại thông tin customer mới nhất vào database
                    updateCustomerPointsInDB(customer);
                } catch (Exception e) {
                    // Logic xử lý nếu việc trừ điểm thất bại (ví dụ: không đủ điểm)
                }
            }
        }

        // 5. Cập nhật trạng thái đơn hàng (State Pattern)
        order.setPaymentStatus(paymentResult.getStatusPayment());
        order.getCurrentState().handlePayment(order, paymentResult.getStatusPayment());

        // 6. Đưa thông tin sang View thành công
        req.setAttribute("orderState", order.getCurrentState().getStateName());
        req.setAttribute("promoType", promo);
        req.setAttribute("paymentMethod", method);
        req.setAttribute("subtotal", subtotal);
        req.setAttribute("discount", discountAmount);
        req.setAttribute("tax", tax);
        req.setAttribute("finalTotal", finalTotal);
        req.setAttribute("transactionId", paymentResult.getTransactionID());
        req.setAttribute("paymentStatus", order.getPaymentStatus().name());

        req.getRequestDispatcher("/WEB-INF/views/staff/success.jsp").forward(req, resp);
    }

    /**
     * Cập nhật số điểm mới của khách hàng xuống Database
     */
    private void updateCustomerPointsInDB(CustomerEntity customer) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            // Đồng bộ trạng thái Entity và lưu xuống DB
            em.merge(customer);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}