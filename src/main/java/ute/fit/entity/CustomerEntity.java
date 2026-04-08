package ute.fit.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customers")
public class CustomerEntity extends PersonEntity {
	private int loyaltyPoints;
	
	@OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;
}
