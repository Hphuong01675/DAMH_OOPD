package ute.fit.entity;

import jakarta.persistence.Entity;

@Entity
public class CustomerEntity extends PersonEntity {
	private int loyaltyPoints;

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	// Alias cho JSP EL: ${customer.points}
	public int getPoints() {
		return loyaltyPoints;
	}
}
