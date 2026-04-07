package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IOrderItemDAO;

import java.util.List;

public class OrderItemDAOImpl implements IOrderItemDAO {

	@Override
	public List<Object[]> findBestSeller() {

	    EntityManager em = JPAUtil.getEntityManager();

	    try {
	        return em.createQuery("""
	            SELECT oi.beverage, SUM(oi.quantity)
	            FROM OrderItemEntity oi
	            GROUP BY oi.beverage
	            ORDER BY SUM(oi.quantity) DESC
	        """)
	        .setMaxResults(1)
	        .getResultList();

	    } finally {
	        em.close();
	    }
	}
}