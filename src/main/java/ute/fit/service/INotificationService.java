package ute.fit.service;

import java.util.List;
import ute.fit.entity.NotificationEntity;
import ute.fit.model.UserDTO;

public interface INotificationService {
	int broadcastToRole(String content, String groupRole, UserDTO currentUser);
    List<NotificationEntity> getNotificationsForUser(String username);
}
