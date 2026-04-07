package ute.fit.service.payment;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class VNPaySimulator implements IVNPayService {

    // Các field thực tế như trong một dự án tích hợp VNPay
    private String vnp_TmnCode = "DUMMY_TMN";
    private String vnp_HashSecret = "DUMMY_SECRET_KEY_1234567890";
    private String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    
    // Thuộc tính để nhận dữ liệu từ Adapter truyền sang
    private double amountToPay;
    private String transactionId;

    public VNPaySimulator(double amountToPay, String transactionId) {
        this.amountToPay = amountToPay;
        this.transactionId = transactionId;
    }

    @Override
    public void processVNPayPayment() {
        // Thực tế hóa bằng logic build Query String của VNPay
        try {
            long vnp_Amount = (long)(this.amountToPay * 100);
            
            StringBuilder hashData = new StringBuilder();
            hashData.append("vnp_Amount=").append(vnp_Amount);
            hashData.append("&vnp_Command=pay");
            hashData.append("&vnp_CreateDate=").append(System.currentTimeMillis());
            hashData.append("&vnp_CurrCode=VND");
            hashData.append("&vnp_IpAddr=127.0.0.1");
            hashData.append("&vnp_Locale=vn");
            hashData.append("&vnp_OrderInfo=").append(URLEncoder.encode("Thanh toan don hang " + transactionId, StandardCharsets.UTF_8.toString()));
            hashData.append("&vnp_OrderType=250000"); // Mã danh mục ngành hàng
            hashData.append("&vnp_ReturnUrl=").append(URLEncoder.encode("http://localhost:8080/vnpay_return", StandardCharsets.UTF_8.toString()));
            hashData.append("&vnp_TmnCode=").append(vnp_TmnCode);
            hashData.append("&vnp_TxnRef=").append(transactionId);
            hashData.append("&vnp_Version=2.1.0");

            // Mã hóa dữ liệu - Thực tế sử dụng HmacSHA512
            // Để hệ thống không gặp lỗi phụ thuộc thư viện, ở đây xuất URL an toàn ra thay vì dùng Hmac thực
            String secureHash = Integer.toHexString(hashData.toString().hashCode()); 
            
            String paymentUrl = vnp_Url + "?" + hashData.toString() + "&vnp_SecureHash=" + secureHash;
            
            System.out.println("================ VNPAY REALISTIC PROCESS ================");
            System.out.println("Generating VNPay URL for Transaction #" + transactionId);
            System.out.println("Total Amount: " + vnp_Amount + " VND (VNPAY Format)");
            System.out.println("Payment Request URL: " + paymentUrl);
            System.out.println("=========================================================");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
