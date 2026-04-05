package ute.fit.service.impl;

import ute.fit.model.Order;
import ute.fit.model.Payment;
import ute.fit.service.IPaymentService;
import ute.fit.service.payment.PaymentProcessor;
import ute.fit.service.payment.CardPaymentProcessor;
import ute.fit.service.payment.CashPaymentProcessor;
import ute.fit.service.payment.VNPAYPaymentProcessor;

public class PaymentServiceImpl implements IPaymentService {

    @Override
    public Payment processOrderPayment(Order order, String paymentMethod) {
        PaymentProcessor processor;

        if (paymentMethod == null) {
            paymentMethod = "cash"; // Mặc định
        }

        switch (paymentMethod) {
            case "vnpay":
                processor = new VNPAYPaymentProcessor();
                break;
            case "card":
                processor = new CardPaymentProcessor("BANK_CODE_XYZ");
                break;
            case "cash":
            default:
                processor = new CashPaymentProcessor();
                break;
        }

        return processor.processPayment(order);
    }
}
