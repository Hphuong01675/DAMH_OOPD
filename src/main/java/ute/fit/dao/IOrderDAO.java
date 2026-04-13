package ute.fit.dao;

import java.time.LocalDate;
import ute.fit.entity.OrderEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderDAO {
    double calculateDailyRevenueByStaff(Long staffId, LocalDate date);
    long countOrdersByStaffAndStatus(Long staffId, LocalDate date, String stateName);
    
    Double getTodayRevenue();
    Long countTodayOrders();
    List<Object[]> getRevenueByWeek();

    OrderEntity findById(Long id);
    void update(OrderEntity entity);
    List<OrderEntity> findAll();
    List<OrderEntity> findPendingAndPaidOrdersToday();
    
    Object[] getBaristaStatsToday(String username);
    List<Object[]> getOrdersByBaristaUsernameToday(String username);
    
    // THÊM: 2 Hàm mới phục vụ Dashboard
    List<Object[]> getTopDrinksByBaristaToday(String username);
    List<LocalDateTime> getOrderDatesByBaristaToday(String username);
    
    void save(OrderEntity order);
}
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
//package ute.fit.dao;
//
//import ute.fit.entity.OrderEntity;
//import java.util.List;
//
//public interface IOrderDAO {
//    // Các phương thức hiện có của bạn
//    Double getTodayRevenue();
//    Long countTodayOrders();
//    List<Object[]> getRevenueByWeek();
//
//    // THÊM CÁC PHƯƠNG THỨC SAU:
//    OrderEntity findById(Long id);
//    void update(OrderEntity entity);
//    List<OrderEntity> findAll();
//    List<Object[]> findPendingOrdersDataToday();
//    
//    Object[] getBaristaStatsToday(String username);
//    List<Object[]> getOrdersByBaristaUsernameToday(String username);
//}
