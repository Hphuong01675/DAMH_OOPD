//package ute.fit.dao;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.Query;
//
//import java.util.List;
//
//public interface IOrderDAO {
//    Double getTodayRevenue();
//    Long countTodayOrders();
//    List<Object[]> getRevenueByWeek();
//}
package ute.fit.dao;

import ute.fit.entity.OrderEntity;
import java.util.List;

public interface IOrderDAO {
    // Các phương thức hiện có của bạn
    Double getTodayRevenue();
    Long countTodayOrders();
    List<Object[]> getRevenueByWeek();

    // THÊM CÁC PHƯƠNG THỨC SAU:
    OrderEntity findById(Long id);
    void update(OrderEntity entity);
    List<OrderEntity> findAll();
    List<Object[]> findPendingOrdersDataToday();
}