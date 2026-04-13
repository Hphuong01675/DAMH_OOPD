package ute.fit.service.payment;

import ute.fit.dao.IPaymentDAO;
import ute.fit.dao.impl.PaymentDAOImpl;
import ute.fit.entity.PaymentEntity;
import ute.fit.model.Order;
import ute.fit.model.Payment;

public class CashPaymentProcessor extends PaymentProcessor {

    private final IPaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    protected boolean executePayment(double amount, Payment payment) {
        System.out.println("Thuc hien thanh toan tien mat: " + amount);
        return true;
    }

    @Override
    protected void savePayment(Payment payment, Order order) {
        PaymentEntity entity = new PaymentEntity();
        entity.setAmount(payment.getAmount());
        entity.setPaymentDate(payment.getPaymentDate());
        entity.setPaymentMethod("CASH");
        entity.setStatus(payment.getStatusPayment());
        entity.setTransactionContent(payment.getTransactionID());
        paymentDAO.save(entity, order != null ? order.getOrderId() : null);
    }
}
