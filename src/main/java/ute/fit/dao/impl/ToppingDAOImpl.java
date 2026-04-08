package ute.fit.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IToppingDAO;
import ute.fit.entity.ToppingEntity;
import ute.fit.model.ToppingDTO;

public class ToppingDAOImpl implements IToppingDAO {
	
	@Override
	public List<ToppingEntity> findAll() {
		try (EntityManager em = JPAUtil.getEntityManager()) {
			return em.createQuery("FROM ToppingEntity", ToppingEntity.class).getResultList();
		}
	}
	
	@Override
	public ToppingDTO findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        return em.find(ToppingDTO.class, id);
    }
}
