package ute.fit.dao;

import java.util.List;
import java.util.Set;

import ute.fit.entity.NotificationEntity;
import ute.fit.model.Roles;

public interface INotificationDAO {
	void insertForRoles(String content, Set<Roles> roles);
	List<NotificationEntity> findByReceiver(String username);
	void insertForSpecificUsers(String content, List<String> usernames);
}
