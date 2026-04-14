package ute.fit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Beverages")
public class BeverageEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private double basePrice; // Giá gốc size S
    
    private String imgUrl;
    
    @Column(name = "isSellable", nullable = false)
    private boolean isSellable = true;
}
