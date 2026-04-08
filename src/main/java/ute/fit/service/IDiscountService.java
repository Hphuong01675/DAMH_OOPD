package ute.fit.service;

import java.util.List;
import java.util.Map;

import ute.fit.entity.CustomerEntity;
import ute.fit.entity.DiscountEntity;

public interface IDiscountService {
    double getPriceWithVoucher(double originalPrice, String promotionCode);
    List<Map<String, String>> getAllPromotions();
    
    boolean canApplyPointPromotion(CustomerEntity customer, String promotionCode);
}
