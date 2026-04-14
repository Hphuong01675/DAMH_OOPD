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
    

    List<Object[]> getTopDrinksByBaristaToday(String username);
    List<LocalDateTime> getOrderDatesByBaristaToday(String username);
    
    void save(OrderEntity order);
}
