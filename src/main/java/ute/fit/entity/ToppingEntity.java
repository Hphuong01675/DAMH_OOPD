package ute.fit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ToppingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toppingID;
    private String name;
    private double price;
}