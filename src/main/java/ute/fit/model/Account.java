package ute.fit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements AccountObserver {
    private String username;
    private String password;
    private boolean state;
    private Roles role;
    private Notification latestNotification;

    @Override
    public void receiveNotification(Notification notification) {
        this.latestNotification = notification;
    }
}
