package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IAccountDAO;
import ute.fit.entity.AccountEntity;
import ute.fit.entity.BaristaEntity;
import ute.fit.entity.StaffEntity;
import ute.fit.model.Roles;


public class AccountDAOImpl implements IAccountDAO {

	@Override
	public AccountEntity findActiveAccount(String identifier, Roles role) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        String jpql = "SELECT a FROM AccountEntity a WHERE a.username = :id AND a.role = :role AND a.state = true";
	        return em.createQuery(jpql, AccountEntity.class)
	                 .setParameter("id", identifier)
	                 .setParameter("role", role)
	                 .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    } finally {
	        em.close();
	    }
	}

	@Override
	public StaffEntity findStaffByUsername(String username) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        String jpql = "SELECT s FROM StaffEntity s WHERE s.account.username = :un";
	        return em.createQuery(jpql, StaffEntity.class)
	                 .setParameter("un", username)
	                 .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    } finally {
	        em.close();
	    }
	}

	@Override
	public BaristaEntity findBaristaByUsername(String username) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        String jpql = "SELECT b FROM BaristaEntity b WHERE b.account.username = :un";
	        return em.createQuery(jpql, BaristaEntity.class)
	                 .setParameter("un", username)
	                 .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    } finally {
	        em.close();
	    }
	}
}
