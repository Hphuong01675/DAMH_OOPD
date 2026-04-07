package ute.fit.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Baristas")
public class BaristaEntity extends PersonEntity {
    private String email;
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private AccountEntity account; // Barista có 1 Account
    
 // 1 Barista - nhiều Order (xử lý đơn)
    @OneToMany(mappedBy = "barista", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;
}