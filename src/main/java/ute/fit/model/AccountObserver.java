package ute.fit.model;

public interface AccountObserver {
    void receiveNotification(Notification notification);
    
    // Để lọc xem thông báo này gửi cho Admin, Staff hay Barista
    Roles getRole();
}
