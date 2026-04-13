package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IPaymentDAO;
import ute.fit.entity.OrderEntity;
import ute.fit.entity.PaymentEntity;

public class PaymentDAOImpl implements IPaymentDAO {

    @Override
    public void save(PaymentEntity entity, Long orderId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            if (orderId != null) {
                OrderEntity orderRef = em.getReference(OrderEntity.class, orderId);
                entity.setOrder(orderRef);
            }
            em.persist(entity);
            trans.commit();
            System.out.println("PaymentDAO: saved PaymentEntity successfully.");
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            System.err.println("JPA error while saving payment: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
