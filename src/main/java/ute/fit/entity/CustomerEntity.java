package ute.fit.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Customers")
public class CustomerEntity extends PersonEntity {
	private int loyaltyPoints;
	
	@OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;
}
