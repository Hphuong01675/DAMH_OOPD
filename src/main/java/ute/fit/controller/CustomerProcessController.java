package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.fit.service.ICustomerService;
import ute.fit.service.impl.CustomerServiceImpl;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/customer/process")
public class CustomerProcessController extends HttpServlet {
    
    private final ICustomerService customerService = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String name = req.getParameter("name"); // Có thể null nếu chỉ tìm kiếm
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
        	Map<String, Object> customer = customerService.getOrCreateCustomer(phone, name);

            if (customer != null) {
                // Trả về JSON thông tin khách hàng
            	String json = String.format(
            		    "{\"success\": true, \"customer\": {\"id\": %d, \"name\": \"%s\", \"phoneNumber\": \"%s\", \"loyaltyPoints\": %d}}",
            		    (Long) customer.get("id"),
            		    (String) customer.get("name"),
            		    (String) customer.get("phoneNumber"),
            		    (Integer) customer.get("loyaltyPoints")
            		);
                resp.getWriter().write(json);
            } else {
                // Không tìm thấy và không có lệnh tạo mới
                resp.getWriter().write("{\"success\": false}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"success\": false, \"message\": \"Lỗi xử lý server\"}");
        }
    }
}