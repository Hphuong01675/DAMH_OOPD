package ute.fit.service.strategy;

public class PercentDiscountStrategy implements DiscountStrategy {
    private double percentage;

    public PercentDiscountStrategy(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice - (originalPrice * percentage / 100);
    }
}
