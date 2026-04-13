package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ute.fit.service.ICustomerService;
import ute.fit.service.impl.CustomerServiceImpl;

import java.io.IOException;

@WebServlet("/api/customer/process")
public class CustomerProcessController extends HttpServlet {
    
    private final ICustomerService customerService = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        // Bỏ nhận param "name" ở doGet vì GET giờ chỉ dùng để tìm kiếm
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Gọi trực tiếp hàm trả về JSON từ Service
            String jsonResult = customerService.findCustomerJson(phone);

            if (jsonResult != null) {
                resp.getWriter().write(jsonResult);
            } else {
                resp.getWriter().write("{\"success\": false}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"success\": false, \"message\": \"Lỗi xử lý server\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String name = req.getParameter("name");
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // POST dùng để tạo mới
            String jsonResult = customerService.createCustomerJson(phone, name);
            if (jsonResult != null) {
                resp.getWriter().write(jsonResult);
            } else {
                resp.getWriter().write("{\"success\": false, \"message\": \"Không thể lưu khách hàng vào Database\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"success\": false, \"message\": \"Lỗi DB: " + e.getMessage() + "\"}");
        }
    }
}