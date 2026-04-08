package ute.fit.service.impl;

import java.util.ArrayList;
import java.util.List;

import ute.fit.dao.NotificationDAO;
import ute.fit.entity.NotificationEntity;
import ute.fit.model.NotificationDTO;
import ute.fit.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {

    private NotificationDAO repo = new NotificationDAO();

    @Override
    public List<NotificationDTO> getByUser(String username) {

        List<NotificationEntity> entities = repo.findByUsername(username);

        List<NotificationDTO> list = new ArrayList<>();

        for (NotificationEntity e : entities) {
            list.add(new NotificationDTO(
            		e.getNotificationID(),
                    e.getContent(),
                    e.getSentDate().toString()
            ));
        }

        return list;
    }
}