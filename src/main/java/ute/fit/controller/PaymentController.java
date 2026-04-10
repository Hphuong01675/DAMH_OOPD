package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.model.Order;
import ute.fit.model.Payment;
import ute.fit.model.PaymentResultDTO;
import ute.fit.model.UserDTO;
import ute.fit.service.IDiscountService;
import ute.fit.service.IOrderService;
import ute.fit.service.impl.DiscountServiceImpl;
import ute.fit.service.impl.OrderServiceImpl;
import ute.fit.service.payment.CardPaymentProcessor;
import ute.fit.service.payment.CashPaymentProcessor;
import ute.fit.service.payment.PaymentProcessor;
import ute.fit.service.payment.VNPAYPaymentProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet({"/payment", "/admin/order/confirm", "/staff/payment/confirm", "/staff/order/confirm"})
public class PaymentController extends HttpServlet {

    private final IDiscountService discountService = new DiscountServiceImpl();
    private final IOrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Object accountObj = (session != null) ? session.getAttribute("user") : null;

        if (accountObj == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserDTO user = (UserDTO) accountObj;
        Order order = null;

        if (session != null) {
            Object maybeOrder = session.getAttribute("order");
            if (maybeOrder instanceof Order o) {
                order = o;
            }
        }

        if (order == null || order.getItems().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/staff/order");
            return;
        }

        // Persist 1 lan duy nhat khi chua co orderId de tranh tao don trung lap khi refresh.
        if (order.getOrderId() == null) {
            Long orderId = orderService.saveOrder(order, user);
            order.setOrderId(orderId);
            session.setAttribute("order", order);
        }

        double subtotal = order.calculateTotal();
        List<Map<String, String>> promotions = discountService.getAllPromotions();

        req.setAttribute("orderItems", order.getItems());
        req.setAttribute("subtotal", subtotal);
        req.setAttribute("orderId", order.getOrderId());
        req.setAttribute("promotions", promotions);

        req.getRequestDispatcher("/WEB-INF/views/staff/payment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/staff/order");
            return;
        }

        Order order = (Order) session.getAttribute("order");
        if (order == null || order.getItems().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/staff/order");
            return;
        }

        String method = req.getParameter("paymentMethod");
        String promo = req.getParameter("promoStrategy");
        Long customerId = parseLong(req.getParameter("customerId"));

        if (customerId != null) {
            order.setCustomerId(customerId);
            session.setAttribute("order", order);
        }

        double subtotal = order.calculateTotal();
        double totalAfterDiscount = discountService.getPriceWithVoucher(subtotal, promo);
        double discountAmount = Math.max(0, subtotal - totalAfterDiscount);
        double finalTotal = Math.max(0, totalAfterDiscount);

        PaymentProcessor processor;
        if ("vnpay".equals(method)) {
            processor = new VNPAYPaymentProcessor();
        } else if ("card".equals(method)) {
            processor = new CardPaymentProcessor("VPBANK");
        } else {
            method = "cash";
            processor = new CashPaymentProcessor();
        }

        Payment paymentResult = processor.processPayment(order);

        order.setPaymentStatus(paymentResult.getStatusPayment());
        order.getCurrentState().handlePayment(order, paymentResult.getStatusPayment());

        orderService.handlePostPayment(
                order.getOrderId(),
                customerId,
                promo,
                paymentResult.getStatusPayment(),
                order.getCurrentState().getStateName()
        );

        PaymentResultDTO resultDTO = new PaymentResultDTO(
                paymentResult.getTransactionID(),
                order.getPaymentStatus().name(),
                order.getCurrentState().getStateName(),
                method,
                (promo == null || promo.isBlank()) ? "NONE" : promo,
                subtotal,
                discountAmount,
                finalTotal
        );

        req.setAttribute("paymentResult", resultDTO);
        req.getRequestDispatcher("/WEB-INF/views/staff/success.jsp").forward(req, resp);
    }

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
