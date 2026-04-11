package ute.fit.service.payment;

import ute.fit.dao.IPaymentDAO;
import ute.fit.dao.impl.PaymentDAOImpl;
import ute.fit.entity.PaymentEntity;
import ute.fit.model.Payment;

public class CardPaymentProcessor extends PaymentProcessor {

    private final String bankCode;
    private final IPaymentDAO paymentDAO = new PaymentDAOImpl();

    public CardPaymentProcessor(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    protected boolean executePayment(double amount, Payment payment) {
        System.out.println("Processing Card Payment of amount: " + amount);
        System.out.println("Bank Code connecting: " + bankCode);
        System.out.println("Card transaction successfully processed!");
        return true;
    }

    @Override
    protected void savePayment(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setAmount(payment.getAmount());
        entity.setPaymentDate(payment.getPaymentDate());
        entity.setPaymentMethod("CARD");
        entity.setStatus(payment.getStatusPayment());
        entity.setTransactionContent(payment.getTransactionID());
        paymentDAO.save(entity);
    }
}
