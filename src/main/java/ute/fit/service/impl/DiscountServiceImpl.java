package ute.fit.service.impl;

import ute.fit.service.IDiscountService;
import ute.fit.service.strategy.DiscountStrategy;
import ute.fit.service.strategy.FixedDiscountStrategy;
import ute.fit.service.strategy.PercentDiscountStrategy;
import ute.fit.service.strategy.PointRedeemDiscountStrategy;

public class DiscountServiceImpl implements IDiscountService {
    
    @Override
    public double getPriceWithVoucher(double originalPrice, String promotionCode) {
        DiscountStrategy strategy;
        
        if (promotionCode == null || promotionCode.isEmpty()) {
            return originalPrice;
        }

        switch (promotionCode) {
            case "percent":
                // Giảm 10%
                strategy = new PercentDiscountStrategy(10);
                break;
            case "fixed":
                // Giảm 50.000đ
                strategy = new FixedDiscountStrategy(50000);
                break;
            case "points":
                // Giả sử dùng 10 điểm (mỗi điểm 1000 VND -> giảm 10.000đ)
                strategy = new PointRedeemDiscountStrategy(10);
                break;
            default:
                return originalPrice;
        }

        return strategy.applyDiscount(originalPrice);
    }
}
