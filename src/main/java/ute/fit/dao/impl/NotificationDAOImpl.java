package ute.fit.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.dao.INotificationDAO;
import ute.fit.entity.NotificationEntity;

public class NotificationDAOImpl implements INotificationDAO {
	@Override
    public void insert(NotificationEntity notification) {
        EntityManager em = JPAUtil.getEntityManager(); // Sử dụng JPAUtil của bạn
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(notification);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
	
	@Override
    public List<NotificationEntity> findByReceiver(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT n FROM NotificationEntity n WHERE n.receiver.username = :username ORDER BY n.sentDate DESC";
            return em.createQuery(jpql, NotificationEntity.class)
                    .setParameter("username", username)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}