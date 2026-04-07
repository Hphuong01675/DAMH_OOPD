package ute.fit.entity;

import jakarta.persistence.*;
import ute.fit.model.Roles;

@Entity
@Table(name = "Accounts")
public class AccountEntity {
    @Id
    private String username;
    
    @Column(nullable = false)
    private String password;
    private boolean state; // Trạng thái hoạt động
    
    @Enumerated(EnumType.STRING)
    private Roles role; 

    // Khi Login, tài khoản này sẽ được add vào danh sách Observer trong NotificationManager
}
