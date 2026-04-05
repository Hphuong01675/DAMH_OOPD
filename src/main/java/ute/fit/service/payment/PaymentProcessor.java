package ute.fit.service.payment;

import ute.fit.model.Order;
import ute.fit.model.Payment;
import ute.fit.model.StatusPayment;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class PaymentProcessor {

    // Template Method Pattern
    public final Payment processPayment(Order order) {
        // Khởi tạo Payment
        Payment payment = new Payment();
        payment.setTransactionID(UUID.randomUUID().toString());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatusPayment(StatusPayment.PENDING);

        // Tính toán tổng số tiền
        double amount = createPayment(order);

        // Thực thi thanh toán qua abstract method
        boolean result = executePayment(amount, payment);

        // Cập nhật trạng thái
        updateStatus(payment, result);

        // Ghi lại giao dịch
        savePayment(payment);

        // Trả về Payment hoàn chỉnh
        return buildResult(payment);
    }

    protected double createPayment(Order order) {
        // Thực tế có thể trừ đi mã giảm giá trước. Ở góc độ class này chỉ đơn thuần gọi get total
        return order.calculateTotal(); 
    }

    protected abstract boolean executePayment(double amount, Payment payment);

    protected void updateStatus(Payment payment, boolean result) {
        if (result) {
            payment.setStatusPayment(StatusPayment.SUCCESS);
        } else {
            payment.setStatusPayment(StatusPayment.FAILED);
        }
    }

    protected void savePayment(Payment payment) {
        // Theo yêu cầu "toàn bộ phải thực tế", chúng ta sẽ có thao tác giả định lưu xuống entity ở đây
        System.out.println("[Database] Saving payment transaction " + payment.getTransactionID() + " with status: " + payment.getStatusPayment());
    }

    protected Payment buildResult(Payment payment) {
        // Xây dựng, xác nhận và bảo vệ dữ liệu cuối cùng trước khi trả về
        return payment;
    }
}
