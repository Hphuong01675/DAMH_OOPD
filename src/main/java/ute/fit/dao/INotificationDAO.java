package ute.fit.dao;

import java.util.List;

import ute.fit.entity.NotificationEntity;

public interface INotificationDAO {
	public List<NotificationEntity> findByUsername(String username);
}
