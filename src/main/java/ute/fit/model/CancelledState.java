package ute.fit.model;

public class CancelledState implements OrderState {
    @Override
    public void handleRequest(Order context) {
        System.out.println("Order is cancelled. Cannot process.");
    }

    @Override
    public void cancel(Order context, String reason) {
        System.out.println("Order is already cancelled.");
    }

    @Override
    public String getStateName() { return "CANCELLED"; }
}