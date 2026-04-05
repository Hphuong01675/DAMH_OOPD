package ute.fit.service.impl;

import ute.fit.dao.IDiscountDAO;
import ute.fit.dao.impl.DiscountDAOImpl;
import ute.fit.entity.DiscountEntity;
import ute.fit.service.IDiscountService;
import ute.fit.service.factory.DiscountStrategyFactory;
import ute.fit.service.strategy.DiscountStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscountServiceImpl implements IDiscountService {
    
    // Khai báo DAO (Phụ thuộc vào Interface, tuân thủ Dependency Inversion)
    private final IDiscountDAO discountDAO;

    public DiscountServiceImpl() {
        // Khởi tạo lớp thực thi của DAO
        this.discountDAO = new DiscountDAOImpl(); 
    }

    @Override
    public double getPriceWithVoucher(double originalPrice, String promotionCode) {
        if (promotionCode == null || promotionCode.trim().isEmpty()) {
            return originalPrice;
        }

        // 1. Lấy dữ liệu từ Database thật thông qua DAO
        DiscountEntity voucher = discountDAO.findByCode(promotionCode);

        if (voucher == null) {
            return originalPrice;
        }

        // 2. Giao phó cho Factory tạo đối tượng Strategy dựa vào cấu hình lấy từ Database
        DiscountStrategy strategy = DiscountStrategyFactory.createStrategy(voucher);

        if (strategy == null) {
            return originalPrice;
        }

        // 3. Thực thi tính toán (Strategy Pattern)
        return strategy.applyDiscount(originalPrice);
    }

    @Override
    public List<Map<String, String>> getAllPromotions() {
        List<Map<String, String>> promotionsList = new ArrayList<>();
        
        // Kéo toàn bộ dữ liệu từ bảng trong Database thông qua DAO
        List<DiscountEntity> entities = discountDAO.findAll();
        
        // Trích xuất dữ liệu từ Entity đưa vào Map để che giấu Entity 
        // (Bảo vệ dữ liệu, không cho rò rỉ trực tiếp ra Controller/View)
        for (DiscountEntity entity : entities) {
            promotionsList.add(Map.of(
                "id", entity.getCode(),
                "title", entity.getTitle(),
                "strategyName", entity.getStrategyName()
            ));
        }
        return promotionsList;
    }
}