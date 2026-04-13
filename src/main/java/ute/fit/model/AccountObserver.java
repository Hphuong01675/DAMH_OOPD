package ute.fit.model;

public interface AccountObserver {
    // Khớp với sơ đồ lớp: trả về String và nhận thông báo
    void receiveNotification(Notification notification);
    
    // Để lọc xem thông báo này gửi cho Admin, Staff hay Barista
    Roles getRole();
}
