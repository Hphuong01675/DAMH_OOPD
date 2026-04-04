package ute.fit.model;

public class PendingState implements OrderState {
    @Override
    public void handleRequest(Order context) {
        // Business Logic: Send to Barista queue
        System.out.println("Order " + context.getOrderId() + " is now being prepared by the Barista.");
        context.setState(new CompletedState());
    }

    @Override
    public void cancel(Order context, String reason) {
        System.out.println("Order cancelled from Pending. Reason: " + reason);
        context.setCancelReason(reason);
        context.setState(new CancelledState());
    }

    @Override
    public String getStateName() { return "PENDING"; }
}