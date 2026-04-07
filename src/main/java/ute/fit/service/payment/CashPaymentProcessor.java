package ute.fit.service.payment;

import ute.fit.model.Payment;

public class CashPaymentProcessor extends PaymentProcessor {
    
    @Override
    protected boolean executePayment(double amount, Payment payment) {
        System.out.println("Processing Cash Payment of amount: " + amount);
        System.out.println("Cash transaction successful!");
        // Tiền mặt coi như trao đổi trực tiếp nên tỷ lệ thành công là 100% khi nhân viên xác nhận
        return true; 
    }
}
