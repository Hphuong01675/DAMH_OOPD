package ute.fit.service;

import java.util.Map;
import ute.fit.entity.OrderEntity;
import ute.fit.model.Order;
import ute.fit.model.StatusPayment;
import ute.fit.model.UserDTO;

import java.util.List;

public interface IOrderService {
    Map<String, Object> getStaffDailyStats(Long staffId);
    
    void processOrder(Long orderId);
    void updateState(Long orderId, String action, String reason);
    List<OrderEntity> getPendingOrdersToday();
    List<Object[]> getPendingOrdersDataToday();
    
    Object[] getBaristaStatsToday(String username);
    List<Object[]> getOrdersByBaristaUsernameToday(String username);
    
    List<Map<String, Object>> getBaristaChartDataToday(String username);
    List<Object[]> getTopDrinksByBaristaToday(String username);
    
    Long saveOrder(Order order, UserDTO userDto);
    void handlePostPayment(Long orderId, Long customerId, String promoCode, StatusPayment paymentStatus, String orderStateName);
}
