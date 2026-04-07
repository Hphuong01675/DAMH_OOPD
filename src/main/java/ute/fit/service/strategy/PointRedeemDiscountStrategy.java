package ute.fit.service.strategy;

public class PointRedeemDiscountStrategy implements DiscountStrategy {
    private int pointsPerThousandDong;

    // Ví dụ: người dùng nhập bao nhiêu điểm, mỗi điểm tương ứng bao nhiêu đồng
    public PointRedeemDiscountStrategy(int pointsPerThousandDong) {
        this.pointsPerThousandDong = pointsPerThousandDong;
    }

    @Override
    public double applyDiscount(double originalPrice) {
        // Giả sử mỗi điểm trị giá 1000 đồng
        double discountAmount = pointsPerThousandDong * 1000;
        double discountedPrice = originalPrice - discountAmount;
        return discountedPrice > 0 ? discountedPrice : 0;
    }
}
