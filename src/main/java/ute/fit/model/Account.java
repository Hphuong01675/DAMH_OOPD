package ute.fit.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"username", "role"})
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
