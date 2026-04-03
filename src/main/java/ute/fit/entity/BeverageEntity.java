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
    private String name;
    private double basePrice; // Giá gốc size S
}
