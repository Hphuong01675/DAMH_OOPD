package ute.fit.service.factory;

import ute.fit.entity.DiscountEntity;
import ute.fit.service.strategy.DiscountStrategy;
import ute.fit.service.strategy.FixedDiscountStrategy;
import ute.fit.service.strategy.PercentDiscountStrategy;
import ute.fit.service.strategy.PointRedeemDiscountStrategy;

public class DiscountStrategyFactory {

    public static DiscountStrategy createStrategy(DiscountEntity entity) {
        if (entity == null || entity.getStrategyType() == null) {
            return null;
        }

        switch (entity.getStrategyType().toUpperCase()) {
            case "PERCENT":
                return new PercentDiscountStrategy(entity.getDiscountValue());
            case "FIXED":
                return new FixedDiscountStrategy(entity.getDiscountValue());
            case "POINT_REDEEM":
                return new PointRedeemDiscountStrategy((int) entity.getDiscountValue());
            default:
                throw new IllegalArgumentException("Loại giảm giá không hợp lệ!");
        }
    }
}