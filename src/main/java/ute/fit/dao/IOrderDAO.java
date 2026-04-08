package ute.fit.dao;

import java.time.LocalDate;

public interface IOrderDAO {
    double calculateDailyRevenueByStaff(Long staffId, LocalDate date);
    long countOrdersByStaffAndStatus(Long staffId, LocalDate date, String stateName);
}