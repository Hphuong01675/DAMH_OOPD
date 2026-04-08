package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.entity.NotificationEntity;

public class NotificationDaoImpl {
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

    public java.util.List<NotificationEntity> findByReceiver(String username) {
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