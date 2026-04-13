package ute.fit.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ute.fit.model.IceLevel;
import ute.fit.model.Size;
import ute.fit.model.SugarLevel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderItems")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private OrderEntity order;
    @ManyToOne
    private BeverageEntity beverage;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ToppingEntity> toppings;
    
    // Lựa chọn của khách
    @Enumerated(EnumType.STRING)
    private Size size;
    
    @Enumerated(EnumType.STRING)
    private SugarLevel sugar;
    
    @Enumerated(EnumType.STRING)
    private IceLevel ice;
    
    private int quantity;
    private double unitPrice; // GIÁ TẠI THỜI ĐIỂM MUA (Beverage + Size + Toppings)
}
