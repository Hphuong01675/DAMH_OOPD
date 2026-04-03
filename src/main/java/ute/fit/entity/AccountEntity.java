package ute.fit.entity;

import jakarta.persistence.*;
import ute.fit.model.Roles;

@Entity
public class AccountEntity {
    @Id
    private String username;
    private String password;
    private boolean state; // Trạng thái hoạt động
    
    @Enumerated(EnumType.STRING)
    private Roles role; 
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    // Khi Login, tài khoản này sẽ được add vào danh sách Observer trong NotificationManager
}
