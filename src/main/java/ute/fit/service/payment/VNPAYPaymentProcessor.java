package ute.fit.service.payment;

import ute.fit.dao.IPaymentDAO;
import ute.fit.dao.impl.PaymentDAOImpl;
import ute.fit.entity.PaymentEntity;
import ute.fit.model.Order;
import ute.fit.model.Payment;

public class VNPAYPaymentProcessor extends PaymentProcessor {
    private final IVNPayGateway gateway;
    private final IPaymentDAO paymentDAO = new PaymentDAOImpl();

    public VNPAYPaymentProcessor() {
        this.gateway = new VNPayAdapter();
    }

    @Override
    protected boolean executePayment(double amount, Payment payment) {
        System.out.println("Bat dau xu ly VNPay...");
        // Client chi biet Target interface, khong phu thuoc Adaptee API
        return gateway.executePayment(amount, payment.getTransactionID());
    }

    @Override
    protected void savePayment(Payment payment, Order order) {
        PaymentEntity entity = new PaymentEntity();
        entity.setAmount(payment.getAmount());
        entity.setPaymentDate(payment.getPaymentDate());
        entity.setPaymentMethod("VNPAY");
        entity.setStatus(payment.getStatusPayment());
        entity.setTransactionContent(payment.getTransactionID());
        paymentDAO.save(entity, order != null ? order.getOrderId() : null);
    }
}
