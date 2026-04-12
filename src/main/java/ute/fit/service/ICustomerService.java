package ute.fit.service;

import java.util.Map;

public interface ICustomerService {
    
    // Tìm kiếm khách hàng và trả về chuỗi JSON trực tiếp
    String findCustomerJson(String phone);
    
    // Tạo khách hàng mới và trả về chuỗi JSON trực tiếp
    String createCustomerJson(String phone, String name);
    
    // BẮT BUỘC THÊM HÀM NÀY: Tìm khách hàng theo ID và trả về JSON (Dùng cho PaymentController)
    String findCustomerByIdJson(Long id); 
    
    // Trừ điểm tích lũy của khách hàng
    boolean deductCustomerPoints(Long customerId, int pointsToDeduct);
    
    Map<String, String> getCustomerDataMap(Long id);
}