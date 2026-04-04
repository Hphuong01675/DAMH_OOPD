package ute.fit.service;

import java.util.List;

import ute.fit.entity.OrderEntity;

public interface IOrderService {
    void processOrder(Long orderId);
    void updateState(Long orderId, String action, String reason);
    List<OrderEntity> getPendingOrdersToday();
    List<Object[]> getPendingOrdersDataToday();
}
