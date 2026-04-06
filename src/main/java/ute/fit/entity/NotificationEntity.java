package ute.fit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class NotificationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationID;
    private String content;
    private LocalDateTime sentDate;
    @ManyToOne
    private AccountEntity receiver;

    public void makeNewNotification() {
        this.sentDate = LocalDateTime.now();
    }
}
