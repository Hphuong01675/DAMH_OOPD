package ute.fit.service.payment;

public interface IVNPayGateway {
    boolean executePayment(double amount, String transactionId);
}
