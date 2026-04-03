package ute.fit.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import ute.fit.model.StatusPayment;

@Entity
public class OrderEntity {
    @Id
    private Long orderID;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String stateName; // Cho State Pattern
    
    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;
    
    @ManyToOne
    private CustomerEntity customer;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;
}
