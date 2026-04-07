package ute.fit.service;

import java.util.Map;

public interface IDashboardService {
    double getTodayRevenue();
    long getTodayOrders();
    // Thay đổi từ BeverageEntity sang Map
    Map<String, Object> getBestSellerInfo(); 
    Map<String, Double> getRevenueByWeek();
}