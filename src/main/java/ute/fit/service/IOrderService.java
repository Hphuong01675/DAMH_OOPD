package ute.fit.service;

import java.util.Map;
import ute.fit.entity.OrderEntity;
import ute.fit.model.Order;
import ute.fit.model.UserDTO;

import java.util.List;

public interface IOrderService {
    Map<String, Object> getStaffDailyStats(Long staffId);
    
    void processOrder(Long orderId, Long baristaId);
    void updateState(Long orderId, String action, String reason, Long baristaId);
    List<OrderEntity> getPendingOrdersToday();
    List<OrderEntity> getPendingAndPaidOrdersToday();
    
    Object[] getBaristaStatsToday(String username);
    List<Object[]> getOrdersByBaristaUsernameToday(String username);
    
    List<Map<String, Object>> getBaristaChartDataToday(String username);
    List<Object[]> getTopDrinksByBaristaToday(String username);
    
    void saveOrder(Order order, UserDTO userDto);
}
