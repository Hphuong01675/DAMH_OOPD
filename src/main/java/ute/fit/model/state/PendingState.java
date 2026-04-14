package ute.fit.model.state;

import ute.fit.dao.IOrderDAO;
import ute.fit.entity.OrderEntity;
import ute.fit.model.Order;
import ute.fit.model.StatusPayment;

public class PendingState implements OrderState {

    @Override
    public void handlePayment(Order context, StatusPayment status, IOrderDAO orderDAO) {
        if (status == StatusPayment.SUCCESS) {
            // 1. Cập nhật trạng thái nội bộ của Model
            context.setPaymentStatus(StatusPayment.SUCCESS);
            
            // 2. Tìm Entity và cập nhật Database thực tế
            if (context.getOrderId() != null) {
                OrderEntity entity = orderDAO.findById(context.getOrderId());
                if (entity != null) {
                    entity.setStatusPayment(StatusPayment.SUCCESS);
                    orderDAO.update(entity);
                }
            }
        } else if (status == StatusPayment.FAILED) {
            // Nếu thất bại: Chuyển sang Cancelled
            context.setPaymentStatus(StatusPayment.FAILED);
            this.cancel(context, "Thanh toán thất bại hoặc bị hủy.", orderDAO);
        }
    }

    @Override
    public void handleRequest(Order context, IOrderDAO orderDAO) {
        // 1. Chuyển Model sang trạng thái hoàn thành
        context.setState(new CompletedState());
        
        // 2. Cập nhật StateName xuống Database
        if (context.getOrderId() != null) {
            OrderEntity entity = orderDAO.findById(context.getOrderId());
            if (entity != null) {
                entity.setStateName(context.getCurrentState().getStateName());
                orderDAO.update(entity); 
            }
        }
    }

    @Override
    public void cancel(Order context, String reason, IOrderDAO orderDAO) {
        // 1. Cập nhật Model
        context.setCancelReason(reason);
        context.setState(new CancelledState());
        
        // 2. Cập nhật StateName xuống Database
        if (context.getOrderId() != null) {
            OrderEntity entity = orderDAO.findById(context.getOrderId());
            if (entity != null) {
                entity.setStateName(context.getCurrentState().getStateName());
                orderDAO.update(entity);
            }
        }
    }

    @Override
    public String getStateName() { 
        return "PENDING"; 
    }
}