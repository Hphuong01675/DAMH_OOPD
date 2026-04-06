package ute.fit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Notifications")
public class NotificationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime sentAt;
    @ManyToOne
    private AccountEntity receiver;
}
