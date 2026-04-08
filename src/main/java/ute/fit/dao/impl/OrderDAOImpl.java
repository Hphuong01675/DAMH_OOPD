package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IOrderDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderDAOImpl implements IOrderDAO {

    @Override
    public double calculateDailyRevenueByStaff(Long staffId, LocalDate date) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

            // Sử dụng tham số :status và hằng số SUCCESS từ Enum của bạn
            String jpql = "SELECT SUM(o.totalAmount) FROM OrderEntity o " +
                          "WHERE o.staff.id = :staffId " +
                          "AND o.orderDate BETWEEN :start AND :end " +
                          "AND o.statusPayment = :status";
            
            TypedQuery<Double> query = em.createQuery(jpql, Double.class);
            query.setParameter("staffId", staffId);
            query.setParameter("start", startOfDay);
            query.setParameter("end", endOfDay);
            
            // Sửa tại đây: dùng StatusPayment.SUCCESS
            query.setParameter("status", ute.fit.model.StatusPayment.SUCCESS); 
            
            Double result = query.getSingleResult();
            return result != null ? result : 0.0;
        } finally {
            em.close();
        }
    }

    @Override
    public long countOrdersByStaffAndStatus(Long staffId, LocalDate date, String stateName) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);

            // Đếm số đơn theo trạng thái (Completed/Cancelled)
            String jpql = "SELECT COUNT(o) FROM OrderEntity o " +
                          "WHERE o.staff.id = :staffId " +      
                          "AND o.orderDate BETWEEN :start AND :end " +
                          "AND o.stateName = :state";

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("staffId", staffId);
            query.setParameter("start", start);
            query.setParameter("end", end);
            query.setParameter("state", stateName);

            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}