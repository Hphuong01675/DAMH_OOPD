package ute.fit.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    // Thông tin định danh giao dịch
    private String transactionId;
    
    // Thông tin tài chính (Để hiển thị hóa đơn)
    private double subtotal;      // Tổng tiền hàng
    private double discount;      // Số tiền được giảm
    private double tax;           // Thuế (10%)
    private double finalTotal;    // Số tiền cuối cùng phải trả
    
    // Trạng thái và phương thức
    private String paymentMethod; // Cash, VNPAY, Card
    private String status;        // SUCCESS, FAILED
    private String paymentDate;   // Định dạng chuỗi để dễ hiển thị ở View
    
    // Thông tin bổ sung
    private String customerName;
    private String orderState;    // Trạng thái đơn hàng sau khi thanh toán (vđ: COMPLETED)
}