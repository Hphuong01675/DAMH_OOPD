package ute.fit.service.impl;

import ute.fit.dao.IOrderDAO;
import ute.fit.dao.IOrderItemDAO;
import ute.fit.dao.impl.OrderDAOImpl;
import ute.fit.dao.impl.OrderItemDAOImpl;
import ute.fit.service.IDashboardService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardServiceImpl implements IDashboardService {

    private IOrderDAO orderDAO = new OrderDAOImpl();
    private IOrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    @Override
    public double getTodayRevenue() {
        return orderDAO.getTodayRevenue();
    }

    @Override
    public long getTodayOrders() {
        return orderDAO.countTodayOrders();
    }

    @Override
    public Map<String, Double> getRevenueByWeek() {

        List<Object[]> data = orderDAO.getRevenueByWeek();

        Map<String, Double> result = new LinkedHashMap<>();

        for (Object[] row : data) {
            LocalDateTime date = (LocalDateTime) row[0];
            Double revenue = (Double) row[1];

            String dayName = switch (date.getDayOfWeek()) {
                case MONDAY -> "Thứ 2";
                case TUESDAY -> "Thứ 3";
                case WEDNESDAY -> "Thứ 4";
                case THURSDAY -> "Thứ 5";
                case FRIDAY -> "Thứ 6";
                case SATURDAY -> "Thứ 7";
                case SUNDAY -> "Chủ nhật";
            };

            result.put(dayName, result.getOrDefault(dayName, 0.0) + revenue);
        }

        return result;
    }

    @Override
    public Map<String, Object> getBestSellerInfo() {
        List<Object[]> list = orderItemDAO.findBestSeller(); // Trả về [BeverageEntity, quantity] [cite: 12]
        if (list.isEmpty()) return null;

        Object[] result = list.get(0);
        ute.fit.entity.BeverageEntity entity = (ute.fit.entity.BeverageEntity) result[0];
        
        Map<String, Object> info = new HashMap<>();
        try {
            // Dùng Reflection lấy dữ liệu từ trường private [cite: 13, 14]
            java.lang.reflect.Field nameField = entity.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            info.put("name", nameField.get(entity));

            java.lang.reflect.Field imgField = entity.getClass().getDeclaredField("imgUrl");
            imgField.setAccessible(true);
            info.put("imgUrl", imgField.get(entity));
            
            info.put("salesCount", result[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
}