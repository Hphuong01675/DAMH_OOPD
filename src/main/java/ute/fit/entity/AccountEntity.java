package ute.fit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ute.fit.model.Roles;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Accounts")
public class AccountEntity {
    @Id
    private String username;
    
    @Column(nullable = false)
    private String password;
    private boolean state;
    
    @Enumerated(EnumType.STRING)
    private Roles role; 
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private PersonEntity person;
}
