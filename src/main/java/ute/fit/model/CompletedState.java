package ute.fit.model;

public class CompletedState implements OrderState {
    @Override
    public void handleRequest(Order context) {
        // Logic: Update revenue, finalize totals
        System.out.println("Order " + context.getOrderId() + " is already completed. No further action.");
    }

    @Override
    public void cancel(Order context, String reason) {
        // Logic: Refund money and subtract revenue
        System.out.println("Refunding completed order. Reason: " + reason);
        context.setCancelReason(reason);
        context.setState(new CancelledState());
    }

    @Override
    public String getStateName() { return "COMPLETED"; }
}