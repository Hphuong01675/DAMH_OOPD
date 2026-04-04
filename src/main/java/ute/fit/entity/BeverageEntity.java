package ute.fit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BeverageEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;
    
    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String name;
    
    @Column(nullable = false)
    private double basePrice; // Giá gốc size S
    
    private String imgUrl;
    
    @Column(name = "isSellable", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean isSellable = true;
}
