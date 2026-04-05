package ute.fit.service;

import ute.fit.model.Order;
import ute.fit.model.Payment;

public interface IPaymentService {
    Payment processOrderPayment(Order order, String paymentMethod);
}
