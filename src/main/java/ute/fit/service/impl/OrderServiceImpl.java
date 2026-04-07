package ute.fit.service.impl;

import ute.fit.dao.IOrderDAO;
import ute.fit.dao.impl.OrderDAOImpl;
import ute.fit.service.IOrderService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderServiceImpl implements IOrderService {
    private final IOrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public Map<String, Object> getStaffDailyStats(Long staffId) {
        LocalDate today = LocalDate.now();
        Map<String, Object> stats = new HashMap<>();
        
        // Thống kê hỗ trợ kết ca cho Staff
        stats.put("revenue", orderDAO.calculateDailyRevenueByStaff(staffId, today));
        stats.put("completedCount", orderDAO.countOrdersByStaffAndStatus(staffId, today, "Completed"));
        stats.put("cancelledCount", orderDAO.countOrdersByStaffAndStatus(staffId, today, "Cancelled"));
        
        return stats;
    }
}