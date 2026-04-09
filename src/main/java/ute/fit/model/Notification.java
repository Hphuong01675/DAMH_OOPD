package ute.fit.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Notification {
    private int notificationID;
    private String content;
    private LocalDateTime sentDate;

    // Phương thức đúng như trong sơ đồ lớp
    public void makeNewNotification() {
        this.sentDate = LocalDateTime.now();
        // Bạn có thể thêm logic tiền xử lý nội dung ở đây nếu cần
    }
}