//package ute.fit.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class DiscountEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String code;
//    private String strategyType; // PERCENT, FIXED, POINT_REDEEM
//    private double discountValue;
//    
//    public String getStrategyType() { return strategyType; }
//    public double getDiscountValue() { return discountValue; }
//    public String getCode() { return code; }
//}

package ute.fit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DiscountEntity") // Cố định tên bảng trong DB
public class DiscountEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;         // Dùng làm ID (vd: "percent", "fixed")
    
    private String title;        // Tên hiển thị (vd: "10% Member Discount")
    private String strategyName; // Tên chiến lược (vd: "Percentage Discount")
    private String strategyType; // Kiểu chiến lược ("PERCENT", "FIXED", "POINT_REDEEM")
    private double discountValue;// Giá trị giảm (10, 50000, 10)

    // BẮT BUỘC: Constructor rỗng để JPA/Hibernate khởi tạo object
    public DiscountEntity() {
    }

    public DiscountEntity(String code, String title, String strategyName, String strategyType, double discountValue) {
        this.code = code;
        this.title = title;
        this.strategyName = strategyName;
        this.strategyType = strategyType;
        this.discountValue = discountValue;
    }

    // Getters
    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getStrategyName() { return strategyName; }
    public String getStrategyType() { return strategyType; }
    public double getDiscountValue() { return discountValue; }
}