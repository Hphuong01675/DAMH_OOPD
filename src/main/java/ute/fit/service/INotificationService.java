package ute.fit.service;

import java.util.List;
import ute.fit.model.Notification;
import ute.fit.model.UserDTO;

public interface INotificationService {
	void broadcastToRole(String content, String groupRole, UserDTO currentUser);
	List<Notification> getNotificationsForUser(String username);
}
