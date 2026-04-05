package ute.fit.entity;

import jakarta.persistence.Entity;

@Entity
public class StaffEntity extends PersonEntity {
	private String email;
	private String address;
}
