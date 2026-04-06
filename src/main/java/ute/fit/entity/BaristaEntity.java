package ute.fit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Baristas")
public class BaristaEntity extends PersonEntity {
    private String email;
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private AccountEntity account; // Barista có 1 Account
}