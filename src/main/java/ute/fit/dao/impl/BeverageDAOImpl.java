package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IBeverageDAO;
import ute.fit.entity.BeverageEntity;
import java.util.List;

public class BeverageDAOImpl implements IBeverageDAO {
	@Override
	public List<BeverageEntity> findAll() {
		try (EntityManager em = JPAUtil.getEntityManager()) {
			return em.createQuery("FROM BeverageEntity", BeverageEntity.class).getResultList();
		}
	}

	@Override
	public BeverageEntity findById(int id) {
		try (EntityManager em = JPAUtil.getEntityManager()) {
			return em.find(BeverageEntity.class, id);
		}
	}

	@Override
	public void insert(BeverageEntity beverage) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(beverage);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void update(BeverageEntity beverage) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(beverage);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(int id) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			BeverageEntity b = em.find(BeverageEntity.class, id);
			if (b != null)
				em.remove(b);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}