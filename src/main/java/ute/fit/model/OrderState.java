package ute.fit.model;

public interface OrderState {
    // We pass the Order context and optionally a reason (for cancellations)
    void handleRequest(Order context);
    void cancel(Order context, String reason);
    String getStateName();
}