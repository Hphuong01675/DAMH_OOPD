package ute.fit.service;

import java.util.Map;

public interface IOrderService {
    Map<String, Object> getStaffDailyStats(Long staffId);
}