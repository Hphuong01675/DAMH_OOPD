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
}