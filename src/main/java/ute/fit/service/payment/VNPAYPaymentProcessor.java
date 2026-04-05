package ute.fit.service.payment;

import ute.fit.model.Payment;

public class VNPAYPaymentProcessor extends PaymentProcessor {
    private VNPayAdapter adapter;

    public VNPAYPaymentProcessor() {
        this.adapter = new VNPayAdapter();
    }

    @Override
    protected boolean executePayment(double amount, Payment payment) {
        System.out.println("Bắt đầu xử lý VNPay...");
        // Gọi giao dịch thông qua Adapter pattern
        boolean isSuccess = adapter.executePayment(amount);
        return isSuccess;
    }
}
