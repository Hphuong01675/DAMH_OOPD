package ute.fit.service;

import java.util.ArrayList;
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

    public synchronized void addObserver(AccountObserver observer) {
        if (observer == null || observers.contains(observer)) {
            return;
        }
        observers.add(observer);
    }

    public synchronized void removeObserver(AccountObserver observer) {
        if (observer == null) {
            return;
        }
        observers.remove(observer);
    }

    public synchronized boolean hasObserver(AccountObserver observer) {
        if (observer == null) {
            return false;
        }
        return observers.contains(observer);
    }

    public synchronized List<AccountObserver> getObserversByRoles(Set<Roles> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of();
        }

        List<AccountObserver> matchedObservers = new ArrayList<>();
        for (AccountObserver observer : observers) {
            if (roles.contains(observer.getRole())) {
                matchedObservers.add(observer);
            }
        }
        return matchedObservers;
    }

    public synchronized void notifyAll(Notification notification) {
        for (AccountObserver obs : observers) {
            obs.receiveNotification(notification);
        }
    }

    public synchronized void notifyByRoles(Notification notification, Set<Roles> roles) {
        for (AccountObserver obs : observers) {
            if (roles.contains(obs.getRole())) {
                obs.receiveNotification(notification);
            }
        }
    }
}
