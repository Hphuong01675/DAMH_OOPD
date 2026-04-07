package ute.fit.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Staffs")
public class StaffEntity extends PersonEntity {
    private String email;
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private AccountEntity account; // Staff có 1 Account

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<OrderEntity> orders; // Staff tạo nhiều Order
}