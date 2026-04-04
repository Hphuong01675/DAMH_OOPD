package ute.fit.model;

public class OrderStateFactory {
    public static OrderState getState(String stateName) {
        if (stateName == null) return new PendingState();
        
        switch (stateName.toUpperCase()) {
            case "COMPLETED": return new CompletedState();
            case "CANCELLED": return new CancelledState();
            case "PENDING":
            default: return new PendingState();
        }
    }
}