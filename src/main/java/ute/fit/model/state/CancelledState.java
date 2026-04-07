package ute.fit.model.state;

import ute.fit.model.Order;
import ute.fit.model.StatusPayment;

/**
 * Trạng thái Cancelled: Trạng thái kết thúc (Terminal State).
 * Đảm bảo không có hành động tài chính hoặc pha chế nào được thực hiện thêm.
 */
public class CancelledState implements OrderState {

    @Override
    public void handlePayment(Order context, StatusPayment status) {
        // HÀNH ĐỘNG: Chặn thanh toán.
        // Đơn hàng đã bị hủy (có thể đã hoàn tiền), không được phép cập nhật trạng thái thanh toán.
        // Trong dự án Web, bạn có thể ném thông báo hoặc đơn giản là không làm gì để bảo vệ dữ liệu.
    }

    @Override
    public void handleRequest(Order context) {
        // HÀNH ĐỘNG: Chặn pha chế.
        // Đơn hàng đã hủy không được gửi xuống Barista và không được tính doanh thu.
    }

    @Override
    public void cancel(Order context, String reason) {
        // HÀNH ĐỘNG: Không thực hiện lại.
        // Tránh việc hoàn tiền 2 lần hoặc ghi đè lý do hủy gốc.
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}