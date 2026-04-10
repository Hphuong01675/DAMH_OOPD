package ute.fit.service.payment;

import ute.fit.dao.IPaymentDAO;
import ute.fit.dao.impl.PaymentDAOImpl;
import ute.fit.entity.PaymentEntity;
import ute.fit.model.Payment;

public class VNPAYPaymentProcessor extends PaymentProcessor {
    private final VNPayAdapter adapter;
    private final IPaymentDAO paymentDAO = new PaymentDAOImpl();

    public VNPAYPaymentProcessor() {
        this.adapter = new VNPayAdapter();
    }

    @Override
    protected boolean executePayment(double amount, Payment payment) {
        System.out.println("Bat dau xu ly VNPay...");
        // Goi giao dich thong qua Adapter pattern
        return adapter.executePayment(amount);
    }

    @Override
    protected void savePayment(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setAmount(payment.getAmount());
        entity.setPaymentDate(payment.getPaymentDate());
        entity.setPaymentMethod("VNPAY");
        entity.setStatus(payment.getStatusPayment());
        entity.setTransactionContent(payment.getTransactionID());
        paymentDAO.save(entity);
    }
}
