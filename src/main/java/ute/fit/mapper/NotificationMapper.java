package ute.fit.mapper;

import ute.fit.entity.NotificationEntity;
import ute.fit.model.Notification;

public class NotificationMapper {
    public static Notification toDTO(NotificationEntity entity) {
        if (entity == null) return null;
        return new Notification(
            entity.getNotificationID(),
            entity.getContent(),
            entity.getSentDate()
        );
    }
}