package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(BeverageEntity.class, id);
        } finally {
            em.close();
        }
    }
	
	@Override
	public BeverageEntity findByName(String name) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        return em.createQuery("SELECT b FROM BeverageEntity b WHERE b.name = :name", BeverageEntity.class)
	                 .setParameter("name", name)
	                 .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    } finally {
	        em.close();
	    }
	}
	
	@Override
    public List<BeverageEntity> searchByName(String keyword) {
        EntityManager em = JPAUtil.getEntityManager();
        String jpql = "SELECT b FROM BeverageEntity b WHERE b.name LIKE :name";
        return em.createQuery(jpql, BeverageEntity.class)
                 .setParameter("name", "%" + keyword + "%")
                 .getResultList();
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
	public void toggleSellable(int id) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        em.getTransaction().begin();
	        BeverageEntity beverage = em.find(BeverageEntity.class, id);
	        if (beverage != null) {
	            beverage.setSellable(!beverage.isSellable());
	        }
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	}
}