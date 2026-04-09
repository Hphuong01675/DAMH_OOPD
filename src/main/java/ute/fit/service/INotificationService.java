package ute.fit.service;

import java.util.List;

import ute.fit.model.NotificationDTO;

public interface INotificationService {
	List<NotificationDTO> getByUser(String username);
}
