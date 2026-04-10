package ute.fit.service.payment;

import ute.fit.dao.IPaymentDAO;
import ute.fit.dao.impl.PaymentDAOImpl;
import ute.fit.entity.PaymentEntity;
import ute.fit.model.Payment;

public class CashPaymentProcessor extends PaymentProcessor {

    private final IPaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    protected boolean executePayment(double amount, Payment payment) {
        // Doi voi tien mat, staff xac nhan da nhan tien nen mo phong thanh cong
        System.out.println("Thuc hien thanh toan tien mat: " + amount);
        return true;
    }

    @Override
    protected void savePayment(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setAmount(payment.getAmount());
        entity.setPaymentDate(payment.getPaymentDate());
        entity.setPaymentMethod("CASH");
        entity.setStatus(payment.getStatusPayment());
        entity.setTransactionContent(payment.getTransactionID());
        paymentDAO.save(entity);
    }
}
