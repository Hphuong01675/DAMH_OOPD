package ute.fit.model.state;

import ute.fit.dao.IOrderDAO;
import ute.fit.entity.OrderEntity;
import ute.fit.model.Order;
import ute.fit.model.StatusPayment;

public class PendingState implements OrderState {

    @Override
    public void handlePayment(Order context, StatusPayment status, IOrderDAO orderDAO) {
        if (status == StatusPayment.SUCCESS) {
            // 1. Cập nhật trạng thái thanh toán thành công vào Model
            context.setPaymentStatus(status);
            
            // 2. Tìm Entity và cập nhật Database thực tế
            if (context.getOrderId() != null) {
                OrderEntity entity = orderDAO.findById(context.getOrderId());
                if (entity != null) {
                    entity.setStatusPayment(status);
                    orderDAO.update(entity);
                }
            }
        } else if (status == StatusPayment.FAILED) {
            // 1. Nếu thất bại: Chuyển StatusPayment của Model thành FAILED
            context.setPaymentStatus(StatusPayment.FAILED);
            
            // 2. Tự động gọi hàm cancel để hủy đơn hàng
            this.cancel(context, "Thanh toán thất bại hoặc giao dịch bị hủy.", orderDAO);
        }
    }

    @Override
    public void handleRequest(Order context, IOrderDAO orderDAO) {
        // 1. Chuyển Model sang trạng thái hoàn thành (Completed)
        context.setState(new CompletedState());
        
        // 2. Đồng bộ StateName xuống Database
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
        // 1. THÊM MỚI: Đảm bảo trạng thái thanh toán chuyển thành FAILED để không tính doanh thu
        context.setPaymentStatus(StatusPayment.FAILED);

        // 2. Cập nhật Model: Lưu lý do và đổi State sang Cancelled
        context.setCancelReason(reason);
        context.setState(new CancelledState());
        
        // 3. Đồng bộ các thay đổi xuống Database
        if (context.getOrderId() != null) {
            OrderEntity entity = orderDAO.findById(context.getOrderId());
            if (entity != null) {
                // Cập nhật State thành CANCELLED
                entity.setStateName(context.getCurrentState().getStateName());
                
                // Đồng bộ trạng thái thanh toán
                entity.setStatusPayment(context.getPaymentStatus());

                orderDAO.update(entity);
            }
        }
    }

    @Override
    public String getStateName() { 
        return "PENDING"; 
    }
}