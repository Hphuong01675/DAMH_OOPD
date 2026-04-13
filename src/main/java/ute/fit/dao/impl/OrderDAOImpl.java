package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IOrderDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.EntityTransaction;
import ute.fit.entity.OrderEntity;
import ute.fit.model.state.PendingState;
import ute.fit.model.StatusPayment;

public class OrderDAOImpl implements IOrderDAO {

	@Override
	public double calculateDailyRevenueByStaff(Long staffId, LocalDate date) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			LocalDateTime startOfDay = date.atStartOfDay();
			LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

			// Sử dụng tham số :status và hằng số SUCCESS từ Enum của bạn
			String jpql = "SELECT SUM(o.totalAmount) FROM OrderEntity o " + "WHERE o.staff.id = :staffId "
					+ "AND o.orderDate BETWEEN :start AND :end " + "AND o.statusPayment = :status";

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
			String jpql = "SELECT COUNT(o) FROM OrderEntity o " + "WHERE o.staff.id = :staffId "
					+ "AND o.orderDate BETWEEN :start AND :end " + "AND o.stateName = :state";

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
			if (trans.isActive())
				trans.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public List<OrderEntity> findAll() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery("SELECT o FROM OrderEntity o", OrderEntity.class).getResultList();
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
					""", Double.class).setParameter("status", StatusPayment.SUCCESS).setParameter("start", start)
					.setParameter("end", end).getSingleResult();
			return result != null ? result : 0.0;
		} finally {
			em.close();
		}
	}

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
					""", Long.class).setParameter("start", start).setParameter("end", end).getSingleResult();
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
					""", Object[].class).setParameter("status", StatusPayment.SUCCESS).setParameter("start", start)
					.setParameter("end", end).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<OrderEntity> findPendingAndPaidOrdersToday() {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        LocalDate today = LocalDate.now();
	        LocalDateTime start = today.atStartOfDay();
	        LocalDateTime end = today.plusDays(1).atStartOfDay();

	        // Thêm DISTINCT để tránh duplicate dữ liệu khi fetch
	        List<OrderEntity> orders = em.createQuery("""
	            SELECT DISTINCT o FROM OrderEntity o
	            WHERE o.orderDate >= :start AND o.orderDate < :end
	            AND o.stateName = 'PENDING'
	            AND o.statusPayment = :paymentStatus
	            ORDER BY o.orderDate ASC
	        """, OrderEntity.class)
	        .setParameter("start", start)
	        .setParameter("end", end)
	        .setParameter("paymentStatus", ute.fit.model.StatusPayment.SUCCESS)
	        .getResultList();

	        // KHẮC PHỤC LỖI LAZY INITIALIZATION TẠI ĐÂY
	        // Kích hoạt (Initialize) các proxy data trước khi đóng session
	        for (OrderEntity order : orders) {
	            // 1. Kích hoạt list Items
	            order.getItems().size(); 
	            
	            for (ute.fit.entity.OrderItemEntity item : order.getItems()) {
	                // 2. Kích hoạt thông tin Nước uống (Beverage)
	                if (item.getBeverage() != null) {
	                    item.getBeverage().getName(); 
	                }
	                // 3. Kích hoạt list Toppings
	                if (item.getToppings() != null) {
	                    item.getToppings().size();
	                }
	            }
	        }

	        return orders;
	    } finally {
	        em.close();
	    }
	}

	@Override
	public Object[] getBaristaStatsToday(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			LocalDate today = LocalDate.now();
			LocalDateTime start = today.atStartOfDay();
			LocalDateTime end = today.plusDays(1).atStartOfDay();

			String jpql = "SELECT " + "SUM(CASE WHEN o.stateName = 'COMPLETED' THEN o.totalAmount ELSE 0.0 END), "
					+ "COUNT(o.orderID), " + "SUM(CASE WHEN o.stateName = 'PENDING' THEN 1 ELSE 0 END) "
					+ "FROM OrderEntity o " + "WHERE o.barista.account.username = :username "
					+ "AND o.orderDate >= :start AND o.orderDate < :end";

			Object[] result = em.createQuery(jpql, Object[].class).setParameter("username", username)
					.setParameter("start", start).setParameter("end", end).getSingleResult();
			return result != null ? result : new Object[] { 0.0, 0L, 0L };
		} catch (Exception e) {
			e.printStackTrace();
			return new Object[] { 0.0, 0L, 0L };
		} finally {
			em.close();
		}
	}

	@Override
	public List<Object[]> getOrdersByBaristaUsernameToday(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			LocalDate today = LocalDate.now();
			LocalDateTime start = today.atStartOfDay();
			LocalDateTime end = today.plusDays(1).atStartOfDay();

			String jpql = "SELECT o.orderID, c.name, o.stateName, o.statusPayment "
					+ "FROM OrderEntity o LEFT JOIN o.customer c " + "WHERE o.barista.account.username = :username "
					+ "AND o.orderDate >= :start AND o.orderDate < :end " + "ORDER BY o.orderDate DESC";

			return em.createQuery(jpql, Object[].class).setParameter("username", username).setParameter("start", start)
					.setParameter("end", end).getResultList();
		} finally {
			em.close();
		}
	}

	// --- HÀM MỚI CHO TOP DRINKS ---
	@Override
	public List<Object[]> getTopDrinksByBaristaToday(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			LocalDate today = LocalDate.now();
			LocalDateTime start = today.atStartOfDay();
			LocalDateTime end = today.plusDays(1).atStartOfDay();

			String jpql = "SELECT b.name, SUM(i.quantity) " + "FROM OrderItemEntity i " + "JOIN i.order o "
					+ "JOIN i.beverage b " + "WHERE o.barista.account.username = :username "
					+ "AND o.orderDate >= :start AND o.orderDate < :end " + "GROUP BY b.name "
					+ "ORDER BY SUM(i.quantity) DESC";

			return em.createQuery(jpql, Object[].class).setParameter("username", username).setParameter("start", start)
					.setParameter("end", end).setMaxResults(3) // Lấy Top 3
					.getResultList();
		} finally {
			em.close();
		}
	}

	// --- HÀM MỚI CHO CHART (Lấy mốc thời gian của đơn hàng) ---
	@Override
	public List<LocalDateTime> getOrderDatesByBaristaToday(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			LocalDate today = LocalDate.now();
			LocalDateTime start = today.atStartOfDay();
			LocalDateTime end = today.plusDays(1).atStartOfDay();

			String jpql = "SELECT o.orderDate FROM OrderEntity o " + "WHERE o.barista.account.username = :username "
					+ "AND o.orderDate >= :start AND o.orderDate < :end";

			return em.createQuery(jpql, LocalDateTime.class).setParameter("username", username)
					.setParameter("start", start).setParameter("end", end).getResultList();
		} finally {
			em.close();
		}
	}
	
	@Override
	public void save(OrderEntity order) {
	    EntityManager em = JPAUtil.getEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        em.merge(order);  // Lưu Order, cascade sẽ xử lý OrderItem và Topping
	        tx.commit();
	    } catch (Exception e) {
	        if (tx.isActive()) tx.rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	}
}