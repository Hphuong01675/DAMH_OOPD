package ute.fit.model;

public class PendingState implements OrderState {
    @Override
    public void handlePayment(Order context, StatusPayment status) {
        if (status == StatusPayment.SUCCESS) {
            // Nếu thành công: Giữ nguyên Pending, đẩy đơn qua Barista
            context.setPaymentStatus(StatusPayment.SUCCESS);
            context.sendToBaristaQueue();
            context.updateDatabase();
        } else if (status == StatusPayment.FAILED) {
            // Nếu thất bại: Chuyển sang Cancelled
            context.setPaymentStatus(StatusPayment.FAILED);
            this.cancel(context, "Thanh toán thất bại hoặc bị hủy.");
        }
    }

    @Override
    public void handleRequest(Order context) {
        // 1. Ghi nhận doanh thu thực tế
        context.recordRevenue(context.calculateTotal()); 
        
        // 2. Chuyển sang hoàn thành
        context.setState(new CompletedState());
        context.updateDatabase(); 
    }

    @Override
    public void cancel(Order context, String reason) {
        context.setCancelReason(reason);
        context.setState(new CancelledState());
        context.updateDatabase();
    }

    @Override
    public String getStateName() { return "PENDING"; }
}