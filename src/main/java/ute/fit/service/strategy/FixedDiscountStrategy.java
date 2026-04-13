package ute.fit.service.strategy;

public class FixedDiscountStrategy implements DiscountStrategy {
    private double fixedAmount;

    public FixedDiscountStrategy(double fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public double applyDiscount(double originalPrice) {
        double discountedPrice = originalPrice - fixedAmount;
        return discountedPrice > 0 ? discountedPrice : 0;
    }
}
