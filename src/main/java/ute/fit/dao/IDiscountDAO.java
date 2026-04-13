package ute.fit.dao;

import ute.fit.entity.DiscountEntity;
import java.util.List;

public interface IDiscountDAO {
    // Tìm mã giảm giá theo ID/Code nhập vào
    DiscountEntity findByCode(String code);
    
    // Lấy toàn bộ danh sách mã giảm giá
    List<DiscountEntity> findAll();
}