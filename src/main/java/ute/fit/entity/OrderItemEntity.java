package ute.fit.entity;

import java.util.List;

import jakarta.persistence.*;
import ute.fit.model.IceLevel;
import ute.fit.model.Size;
import ute.fit.model.SugarLevel;

@Entity
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private OrderEntity order;
    @ManyToOne
    private BeverageEntity beverage;
    @ManyToMany
    private List<ToppingEntity> toppings;
    
    // Lựa chọn của khách
    private Size size;
    private SugarLevel sugar;
    private IceLevel ice;
    
    private int quantity;
    private double unitPrice; // GIÁ TẠI THỜI ĐIỂM MUA (Beverage + Size + Toppings)
}
