package ute.fit.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import ute.fit.model.StatusPayment;

@Entity
@Table(name = "Orders")
public class OrderEntity {
    @Id
    private Long orderID;
    
    private LocalDateTime orderDate;
    private double totalAmount;
    private String stateName; // Cho State Pattern
    
    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;
    
    @ManyToOne(optional = false) // Bắt buộc phải có Staff tạo đơn
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity staff;
    
    
    @ManyToOne
    @JoinColumn(name = "barista_id")
    private BaristaEntity barista;
    
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;
}
