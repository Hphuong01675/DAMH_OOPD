package ute.fit.service.factory;

import ute.fit.entity.DiscountEntity;
import ute.fit.model.stragety.DiscountStrategy;
import ute.fit.model.stragety.FixedDiscountStrategy;
import ute.fit.model.stragety.PercentDiscountStrategy;
import ute.fit.model.stragety.PointRedeemDiscountStrategy;


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