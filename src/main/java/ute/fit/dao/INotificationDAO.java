package ute.fit.dao;

import java.util.List;

import ute.fit.entity.NotificationEntity;

public interface INotificationDAO {
	void insert(NotificationEntity notification);
    List<NotificationEntity> findByReceiver(String username);
}
