package ute.fit.entity;

import jakarta.persistence.Entity;

@Entity
public class CustomerEntity extends PersonEntity {
	private int loyaltyPoints;
}
