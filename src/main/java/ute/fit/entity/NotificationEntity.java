package ute.fit.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Notifications")
public class NotificationEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationID;

    private String content;
    private LocalDateTime sentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private AccountEntity receiver;
}