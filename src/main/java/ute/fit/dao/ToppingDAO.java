package ute.fit.dao;

import jakarta.persistence.EntityManager;
import ute.fit.model.ToppingDTO;
import ute.fit.config.JPAUtil;

public class ToppingDAO {

    public ToppingDTO findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        return em.find(ToppingDTO.class, id);
    }
}