package ute.fit.service.payment;

import ute.fit.model.Order;
import ute.fit.model.Payment;
import ute.fit.model.StatusPayment;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class PaymentProcessor {

    /**
     * Template Method: Dinh nghia thuat toan thanh toan co dinh
     */
    public final Payment processPayment(Order order) {
        double amount = createPayment(order);

        Payment payment = new Payment();
        payment.setTransactionID("TXN-" + UUID.randomUUID().toString().substring(0, 8));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatusPayment(StatusPayment.PENDING);
        payment.setAmount(amount);

        boolean result = executePayment(amount, payment);
        updateStatus(payment, result);
        savePayment(payment, order);

        return buildResult(payment);
    }

    protected double createPayment(Order order) {
        return order.calculateTotal();
    }

    protected abstract boolean executePayment(double amount, Payment payment);

    protected void updateStatus(Payment payment, boolean result) {
        payment.setStatusPayment(result ? StatusPayment.SUCCESS : StatusPayment.FAILED);
    }

    protected abstract void savePayment(Payment payment, Order order);

    protected Payment buildResult(Payment payment) {
        return payment;
    }
}
