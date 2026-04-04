//package ute.fit.dao.impl;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import jakarta.persistence.EntityManager;
//import ute.fit.config.JPAUtil;
//import ute.fit.dao.IOrderDAO;
//
//public class OrderDAOImpl implements IOrderDAO {
//
//	@Override
//	public Double getTodayRevenue() {
//
//	    EntityManager em = JPAUtil.getEntityManager();
//
//	    try {
//	        LocalDate today = LocalDate.now();
//
//	        LocalDateTime start = today.atStartOfDay();
//	        LocalDateTime end = today.plusDays(1).atStartOfDay();
//
//	        Double result = (Double) em.createQuery("""
//	            SELECT SUM(o.totalAmount)
//	            FROM OrderEntity o
//	            WHERE o.statusPayment = :status
//	            AND o.orderDate >= :start
//	            AND o.orderDate < :end
//	        """)
//	        .setParameter("status", ute.fit.model.StatusPayment.SUCCESS)
//	        .setParameter("start", start)
//	        .setParameter("end", end)
//	        .getSingleResult();
//
//	        return result != null ? result : 0.0;
//
//	    } finally {
//	        em.close();
//	    }
//	}
//
//	@Override
//	public Long countTodayOrders() {
//
//	    EntityManager em = JPAUtil.getEntityManager();
//
//	    try {
//	        LocalDate today = LocalDate.now();
//
//	        LocalDateTime start = today.atStartOfDay();
//	        LocalDateTime end = today.plusDays(1).atStartOfDay();
//
//	        return (Long) em.createQuery("""
//	            SELECT COUNT(o)
//	            FROM OrderEntity o
//	            WHERE o.orderDate >= :start
//	            AND o.orderDate < :end
//	        """)
//	        .setParameter("start", start)
//	        .setParameter("end", end)
//	        .getSingleResult();
//
//	    } finally {
//	        em.close();
//	    }
//	}
//	@Override
//	public List<Object[]> getRevenueByWeek() {
//
//	    EntityManager em = JPAUtil.getEntityManager();
//
//	    try {
//	        LocalDate today = LocalDate.now();
//
//	        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
//	        LocalDateTime start = startOfWeek.atStartOfDay();
//	        LocalDateTime end = start.plusDays(7);
//
//	        return em.createQuery("""
//	            SELECT o.orderDate, SUM(o.totalAmount)
//	            FROM OrderEntity o
//	            WHERE o.statusPayment = :status
//	            AND o.orderDate >= :start
//	            AND o.orderDate < :end
//	            GROUP BY o.orderDate
//	        """)
//	        .setParameter("status", ute.fit.model.StatusPayment.SUCCESS)
//	        .setParameter("start", start)
//	        .setParameter("end", end)
//	        .getResultList();
//
//	    } finally {
//	        em.close();
//	    }
//	}
//}

package ute.fit.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IOrderDAO;
import ute.fit.entity.OrderEntity;
import ute.fit.model.StatusPayment;

public class OrderDAOImpl implements IOrderDAO {

    // --- CÁC PHƯƠNG THỨC MỚI CẬP NHẬT ---

    @Override
    public OrderEntity findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Tìm kiếm theo khóa chính orderID defined trong OrderEntity
            return em.find(OrderEntity.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(OrderEntity entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(entity); // Cập nhật các thay đổi về StateName, StatusPayment...
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<OrderEntity> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM OrderEntity o", OrderEntity.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // --- CÁC PHƯƠNG THỨC THỐNG KÊ HIỆN CÓ CỦA BẠN ---

    @Override
    public Double getTodayRevenue() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime start = today.atStartOfDay();
            LocalDateTime end = today.plusDays(1).atStartOfDay();

            Double result = em.createQuery("""
                SELECT SUM(o.totalAmount)
                FROM OrderEntity o
                WHERE o.statusPayment = :status
                AND o.orderDate >= :start
                AND o.orderDate < :end
            """, Double.class)
            .setParameter("status", StatusPayment.SUCCESS)
            .setParameter("start", start)
            .setParameter("end", end)
            .getSingleResult();

            return result != null ? result : 0.0;
        } finally {
            em.close();
        }
    }

    @Override
    public Long countTodayOrders() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime start = today.atStartOfDay();
            LocalDateTime end = today.plusDays(1).atStartOfDay();

            return em.createQuery("""
                SELECT COUNT(o)
                FROM OrderEntity o
                WHERE o.orderDate >= :start
                AND o.orderDate < :end
            """, Long.class)
            .setParameter("start", start)
            .setParameter("end", end)
            .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> getRevenueByWeek() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDate today = LocalDate.now();
            LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
            LocalDateTime start = startOfWeek.atStartOfDay();
            LocalDateTime end = start.plusDays(7);

            return em.createQuery("""
                SELECT o.orderDate, SUM(o.totalAmount)
                FROM OrderEntity o
                WHERE o.statusPayment = :status
                AND o.orderDate >= :start
                AND o.orderDate < :end
                GROUP BY o.orderDate
            """, Object[].class)
            .setParameter("status", StatusPayment.SUCCESS)
            .setParameter("start", start)
            .setParameter("end", end)
            .getResultList();
        } finally {
            em.close();
        }
    }
    @Override
    public List<Object[]> findPendingOrdersDataToday() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime start = today.atStartOfDay();
            LocalDateTime end = today.plusDays(1).atStartOfDay();

            // Chỉ lọc theo thời gian và trạng thái PENDING
            return em.createQuery("""
                SELECT o.orderID, o.orderDate, c.name, o.totalAmount
                FROM OrderEntity o
                LEFT JOIN o.customer c
                WHERE o.orderDate >= :start 
                AND o.orderDate < :end
                AND o.stateName = 'PENDING'
            """) 
            .setParameter("start", start)
            .setParameter("end", end)
            .getResultList();
        } finally {
            em.close();
        }
    }
}