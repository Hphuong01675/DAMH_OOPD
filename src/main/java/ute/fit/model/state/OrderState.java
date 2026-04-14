package ute.fit.model.state;

import ute.fit.dao.IOrderDAO;
import ute.fit.model.Order;
import ute.fit.model.StatusPayment;

public interface OrderState {
	// Xử lý kết quả thanh toán
    void handlePayment(Order context, StatusPayment status, IOrderDAO orderDAO); 
    
    // Barista bấm hoàn thành pha chế
    void handleRequest(Order context, IOrderDAO orderDAO); 
    
    // Hủy đơn hàng
    void cancel(Order context, String reason, IOrderDAO orderDAO); 
    
    String getStateName();
}