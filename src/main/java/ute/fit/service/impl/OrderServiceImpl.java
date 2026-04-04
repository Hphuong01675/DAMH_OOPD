package ute.fit.service.impl;

import ute.fit.dao.IOrderDAO;
import ute.fit.entity.OrderEntity;
import ute.fit.model.*;
import ute.fit.service.IOrderService;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements IOrderService {
    private final IOrderDAO orderDAO;

    public OrderServiceImpl(IOrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void processOrder(Long orderId) {
        // Gọi updateState với hành động mặc định là COMPLETE
        updateState(orderId, "COMPLETE", null);
    }

    @Override
    public void updateState(Long orderId, String action, String reason) {
        OrderEntity entity = orderDAO.findById(orderId);
        if (entity == null) return;

        try {
            Order order = new Order();
            
            // Dùng Reflection đọc trường "orderID" và "stateName"
            Field idField = OrderEntity.class.getDeclaredField("orderID");
            idField.setAccessible(true);
            order.setOrderId((Long) idField.get(entity));

            Field stateField = OrderEntity.class.getDeclaredField("stateName");
            stateField.setAccessible(true);
            String currentStateName = (String) stateField.get(entity);
            
            // Khởi tạo trạng thái từ Model Factory
            order.setState(OrderStateFactory.getState(currentStateName));

            // Thực thi logic State Pattern
            if ("COMPLETE".equalsIgnoreCase(action)) {
                order.proceed();
                // Cập nhật trạng thái thanh toán SUCCESS
                Field paymentField = OrderEntity.class.getDeclaredField("statusPayment");
                paymentField.setAccessible(true);
                paymentField.set(entity, StatusPayment.SUCCESS);
            } else if ("CANCEL".equalsIgnoreCase(action)) {
                order.cancel(reason);
            }

            // Lưu lại trạng thái mới vào Entity thông qua Reflection
            stateField.set(entity, order.getCurrentState().getStateName());
            
            orderDAO.update(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderEntity> getPendingOrdersToday() {
        List<OrderEntity> allOrders = orderDAO.findAll();
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);

        return allOrders.stream().filter(o -> {
            try {
                // Dùng Reflection để lọc dữ liệu trực tiếp từ các trường private
                Field dateField = OrderEntity.class.getDeclaredField("orderDate");
                dateField.setAccessible(true);
                LocalDateTime orderDate = (LocalDateTime) dateField.get(o);

                Field stateField = OrderEntity.class.getDeclaredField("stateName");
                stateField.setAccessible(true);
                String state = (String) stateField.get(o);

                return orderDate != null && 
                       "PENDING".equalsIgnoreCase(state) && 
                       orderDate.isAfter(startOfDay);
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
    }
    @Override
    public List<Object[]> getPendingOrdersDataToday() {
        return orderDAO.findPendingOrdersDataToday();
    }
}