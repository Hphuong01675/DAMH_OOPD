package ute.fit.dao;

import jakarta.persistence.EntityManager;
import ute.fit.model.Beverage;
import ute.fit.config.JPAUtil;

public class ProductDAO {

    public Beverage findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        return em.find(Beverage.class, id);
    }
}