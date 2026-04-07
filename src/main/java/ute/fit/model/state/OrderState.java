package ute.fit.model.state;

import ute.fit.model.Order;
import ute.fit.model.StatusPayment;

public interface OrderState {
    // Xử lý kết quả thanh toán (Dùng trong PaymentController)
    void handlePayment(Order context, StatusPayment status); 
    
    // Barista bấm hoàn thành pha chế (Dùng trong BaristaController)
    void handleRequest(Order context); 
    
    // Hủy đơn hàng
    void cancel(Order context, String reason); 
    
    String getStateName();
}