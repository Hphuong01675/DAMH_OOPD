package ute.fit.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
	public ToppingEntity findById(int id) { 
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        return em.find(ToppingEntity.class, id); 
	    } finally {
	        em.close();
	    }
	}
	
	@Override
	public ToppingEntity findByName(String name) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        String jpql = "SELECT t FROM ToppingEntity t WHERE t.name = :name";
	        return em.createQuery(jpql, ToppingEntity.class)
	                 .setParameter("name", name)
	                 .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    } finally {
	        em.close();
	    }
	}
}
