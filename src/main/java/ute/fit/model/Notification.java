package ute.fit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Integer notificationID;
    private String content;
    private LocalDateTime sentDate;
    
    public void makeNewNotification() {
        this.sentDate = LocalDateTime.now();
    }
}