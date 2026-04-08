package ute.fit.dao;

import java.util.List;

import ute.fit.config.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ute.fit.entity.NotificationEntity;

public class NotificationDAO {

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