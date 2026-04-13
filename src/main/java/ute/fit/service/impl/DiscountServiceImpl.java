package ute.fit.service.impl;

import ute.fit.dao.IDiscountDAO;
import ute.fit.dao.impl.DiscountDAOImpl;
import ute.fit.entity.CustomerEntity;
import ute.fit.entity.DiscountEntity;
import ute.fit.service.IDiscountService;
import ute.fit.service.factory.DiscountStrategyFactory;
import ute.fit.model.stragety.DiscountStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscountServiceImpl implements IDiscountService {
    
    private final IDiscountDAO discountDAO;

    public DiscountServiceImpl() {
        this.discountDAO = new DiscountDAOImpl(); 
    }

    @Override
    public double getPriceWithVoucher(double originalPrice, String promotionCode) {
        if (promotionCode == null || promotionCode.trim().isEmpty()) {
            return originalPrice;
        }

        DiscountEntity voucher = discountDAO.findByCode(promotionCode);
        if (voucher == null) {
            return originalPrice;
        }

        DiscountStrategy strategy = DiscountStrategyFactory.createStrategy(voucher);
        if (strategy == null) {
            return originalPrice;
        }

        return strategy.applyDiscount(originalPrice);
    }

    @Override
    public List<Map<String, String>> getAllPromotions() {
        List<Map<String, String>> promotionsList = new ArrayList<>();
        List<DiscountEntity> entities = discountDAO.findAll();
        
        for (DiscountEntity entity : entities) {
            promotionsList.add(Map.of(
                "id", entity.getCode(),
                "title", entity.getTitle(),
                "strategyName", entity.getStrategyName(),
                "strategyType", entity.getStrategyType() // Bắt buộc phải có dòng này
            ));
        }
        return promotionsList;
    }
    
    @Override
    public boolean canApplyPointPromotion(CustomerEntity customer, String promotionCode) {
        // Nếu không có khách hàng hoặc không có mã thì không thể áp dụng
        if (customer == null || promotionCode == null) return false;
        
        DiscountEntity voucher = discountDAO.findByCode(promotionCode);
        
        // Kiểm tra nếu là chiến lược đổi điểm (POINT_REDEEM hoặc dựa trên strategyName)
        if (voucher != null && ("POINT_REDEEM".equalsIgnoreCase(voucher.getStrategyType()) 
            || "PointRedeem".equalsIgnoreCase(voucher.getStrategyName()))) {
            
            // Khách hàng phải có từ 30 điểm trở lên
            return customer.getLoyaltyPoints() >= 30;
        }
        return true; 
    }

    @Override
    public double calculateDiscountAmount(double originalPrice, String promotionCode) {
        double priceAfterDiscount = getPriceWithVoucher(originalPrice, promotionCode);
        return originalPrice - priceAfterDiscount;
    }
    
    @Override
    public boolean isPointRedeemVoucher(String promotionCode) {
        if (promotionCode == null || promotionCode.isBlank() || "NONE".equals(promotionCode)) {
            return false;
        }
        DiscountEntity voucher = discountDAO.findByCode(promotionCode);
        
        return voucher != null && ("POINT_REDEEM".equalsIgnoreCase(voucher.getStrategyType()) 
                || "PointRedeem".equalsIgnoreCase(voucher.getStrategyName()));
    }
}