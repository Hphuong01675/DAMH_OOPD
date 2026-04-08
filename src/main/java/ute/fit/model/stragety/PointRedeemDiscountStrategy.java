package ute.fit.model.stragety;

public class PointRedeemDiscountStrategy implements DiscountStrategy {
    private static final int POINTS_TO_REDEEM = 30;
    private static final double DISCOUNT_VALUE = 30000;

    @Override
    public double applyDiscount(double originalPrice) {
        // Trừ 30,000đ nhưng không được để giá âm
        double discountedPrice = originalPrice - DISCOUNT_VALUE;
        return Math.max(discountedPrice, 0);
    }
    
    public int getPointsRequired() {
        return POINTS_TO_REDEEM;
    }
}