package ute.fit.service.payment;

/**
 * Target interface ma payment flow hien tai can su dung.
 */
public interface IVNPayGateway {
    boolean executePayment(double amount, String transactionId);
}
