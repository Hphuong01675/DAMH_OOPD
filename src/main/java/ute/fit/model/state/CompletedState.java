package ute.fit.model.state;

import ute.fit.dao.IOrderDAO;
import ute.fit.entity.OrderEntity;
import ute.fit.model.Order;
import ute.fit.model.StatusPayment;

public class CompletedState implements OrderState {
    
    @Override 
    public void handlePayment(Order context, StatusPayment status, IOrderDAO orderDAO) {}
    
    @Override 
    public void handleRequest(Order context, IOrderDAO orderDAO) {} // Đã xong, không làm gì thêm

    @Override 
    public void cancel(Order context, String reason, IOrderDAO orderDAO) {
        context.setCancelReason(reason);
        context.setState(new CancelledState());
        
        // Cập nhật DB khi chuyển từ Hoàn thành -> Hủy
        if (context.getOrderId() != null) {
            OrderEntity entity = orderDAO.findById(context.getOrderId());
            if (entity != null) {
                entity.setStateName(context.getCurrentState().getStateName());
                orderDAO.update(entity);
            }
        }
    }

    @Override 
    public String getStateName() { return "COMPLETED"; }
}