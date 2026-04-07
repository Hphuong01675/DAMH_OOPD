package ute.fit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import ute.fit.model.AccountObserver;
import ute.fit.model.Notification;
import ute.fit.model.Roles;

public class NotificationManager {
    private static NotificationManager instance;
    private final List<AccountObserver> observers = new ArrayList<>();

    private NotificationManager() {}

    public static synchronized NotificationManager getInstance() {
        if (instance == null) instance = new NotificationManager();
        return instance;
    }

    public void addObserver(AccountObserver observer) { observers.add(observer); }
    public void removeObserver(AccountObserver observer) { observers.remove(observer); }
    public void replaceObservers(Collection<? extends AccountObserver> observers) {
        this.observers.clear();
        this.observers.addAll(observers);
    }

    public void notifyAll(Notification notification) {
        for (AccountObserver obs : observers) {
            obs.receiveNotification(notification);
        }
    }

    public void notifyByRoles(Notification notification, Set<Roles> roles) {
        for (AccountObserver obs : observers) {
            if (roles.contains(obs.getRole())) {
                obs.receiveNotification(notification);
            }
        }
    }
}
