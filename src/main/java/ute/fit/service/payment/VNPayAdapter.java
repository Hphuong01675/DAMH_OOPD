package ute.fit.service.payment;

import java.util.UUID;

public class VNPayAdapter {
    private IVNPayService vnPay;

    public boolean executePayment(double amount) {
        // Khởi tạo Adaptee cụ thể với các tham số tương ứng (thực tế hóa)
        String transactionId = "TXN" + System.currentTimeMillis();
        this.vnPay = new VNPaySimulator(amount, transactionId);
        
        // Gọi hàm của giao diện cũ mà Adapter đóng gói
        this.vnPay.processVNPayPayment();
        
        // Trả về kết quả giao dịch thanh toán (Giả sử mặc định thành công mạng)
        return true; 
    }
}
