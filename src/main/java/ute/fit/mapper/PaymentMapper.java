package ute.fit.mapper;

import ute.fit.model.Order;
import ute.fit.model.Payment;
import ute.fit.model.PaymentDTO;

import java.time.format.DateTimeFormatter;

public class PaymentMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static PaymentDTO toDTO(Order order, Payment payment, String promoType) {
        if (order == null || payment == null) {
            return null;
        }

        double subtotal = order.calculateTotal();

        return PaymentDTO.builder()
                .transactionId(payment.getTransactionID())
                .subtotal(subtotal)
                .discount(0)
                .tax(0)
                .finalTotal(subtotal)
                .paymentMethod(payment.getClass().getSimpleName().replace("Payment", ""))
                .status(payment.getStatusPayment().name())
                .paymentDate(payment.getPaymentDate() != null ? payment.getPaymentDate().format(FORMATTER) : "")
                .customerName(order.getCustomerId() != null ? "Customer #" + order.getCustomerId() : "Guest")
                .orderState(order.getCurrentState() != null ? order.getCurrentState().getStateName() : "N/A")
                .build();
    }
}
