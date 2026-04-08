package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.dao.ICustomerDAO;
import ute.fit.entity.CustomerEntity;

import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO {

    @Override
    public CustomerEntity findByPhone(String phone) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<CustomerEntity> list = em.createQuery(
                    "SELECT c FROM CustomerEntity c WHERE c.phoneNumber = :phone", CustomerEntity.class)
                    .setParameter("phone", phone)
                    .getResultList();

            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(customer);
            trans.commit();
            return customer;
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}