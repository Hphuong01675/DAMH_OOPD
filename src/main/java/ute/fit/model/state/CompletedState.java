package ute.fit.model.state;

import ute.fit.model.Order;
import ute.fit.model.StatusPayment;

public class CompletedState implements OrderState {
    @Override public void handlePayment(Order context, StatusPayment status) {}
    @Override public void handleRequest(Order context) {} // Đã xong, không làm gì thêm

    @Override 
    public void cancel(Order context, String reason) {
        context.setCancelReason(reason);
        context.setState(new CancelledState());
        context.updateDatabase();
    }

    @Override public String getStateName() { return "COMPLETED"; }
}