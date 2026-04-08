package ute.fit.service.impl;

import java.util.ArrayList;
import java.util.List;

import ute.fit.dao.INotificationDAO;
import ute.fit.dao.impl.NotificationDAOImpl;
import ute.fit.entity.NotificationEntity;
import ute.fit.model.NotificationDTO;
import ute.fit.service.INotificationService;

public class NotificationServiceImpl implements INotificationService {

    private INotificationDAO repo = new NotificationDAOImpl();

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