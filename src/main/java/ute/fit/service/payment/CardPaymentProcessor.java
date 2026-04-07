package ute.fit.service.payment;

import ute.fit.model.Payment;
import ute.fit.service.payment.PaymentProcessor;

public class CardPaymentProcessor extends PaymentProcessor {
    
    private String bankCode;
    
    public CardPaymentProcessor(String bankCode) {
        this.bankCode = bankCode;
    }
    
    @Override
    protected boolean executePayment(double amount, Payment payment) {
        System.out.println("Processing Card Payment of amount: " + amount);
        System.out.println("Bank Code connecting: " + bankCode);
        // Mô phỏng kết nối POS Terminal hoặc cổng thanh toán thẻ giả lập
        System.out.println("Card transaction successfully processed!");
        return true; 
    }
}
