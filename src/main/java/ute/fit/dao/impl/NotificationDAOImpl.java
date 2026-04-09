package ute.fit.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import ute.fit.config.JPAUtil;
import ute.fit.dao.INotificationDAO;
import ute.fit.entity.NotificationEntity;

public class NotificationDAOImpl implements INotificationDAO {
	@Override
	public List<NotificationEntity> findByUsername(String username) {
	    EntityManager em = JPAUtil.getEntityManager();

	    String jpql = "SELECT n FROM NotificationEntity n " +
	                  "WHERE n.receiver.username = :username " +
	                  "ORDER BY n.notificationID DESC";

	    return em.createQuery(jpql, NotificationEntity.class)
	             .setParameter("username", username)
	             .getResultList();
	}
}
