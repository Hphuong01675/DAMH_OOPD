package ute.fit.model.state;

import ute.fit.dao.IOrderDAO;
import ute.fit.model.Order;
import ute.fit.model.StatusPayment;

public class CancelledState implements OrderState {

    @Override
    public void handlePayment(Order context, StatusPayment status, IOrderDAO orderDAO) {
        // Chặn thanh toán
    }

    @Override
    public void handleRequest(Order context, IOrderDAO orderDAO) {
        // Chặn pha chế
    }

    @Override
    public void cancel(Order context, String reason, IOrderDAO orderDAO) {
        // Tránh hủy 2 lần
    }

    @Override
    public String getStateName() {
        return "CANCELLED";
    }
}