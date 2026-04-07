package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.fit.model.*;
import ute.fit.service.IBeverageService;
import ute.fit.service.impl.BeverageServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"/order/add-item"})
public class OrderActionController extends HttpServlet {
    private IBeverageService beverageService = new BeverageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            // 1. Lấy thông số từ request (Query Parameters)
            int id = Integer.parseInt(req.getParameter("id"));
            Size size = Size.valueOf(req.getParameter("size"));
            SugarLevel sugar = SugarLevel.valueOf(req.getParameter("sugar"));
            IceLevel ice = IceLevel.valueOf(req.getParameter("ice"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            // 2. Gọi service để tạo đối tượng Beverage thông qua Builder Pattern
            Product beverage = beverageService.getBeverageBuilder(id)
                    .setSugar(sugar)
                    .setSize(size)
                    .setIce(ice)
                    .build();

            // 3. Tạo OrderItem (Chuẩn bị cho bước thêm Topping tiếp theo)
            OrderItem orderItem = new OrderItem(beverage, quantity);

            // 4. Tạm thời in ra console để kiểm tra kết quả
            System.out.println("Đã tạo món thành công:");
            System.out.println("Mô tả: " + orderItem.getLineDescription());
            System.out.println("Tổng giá: " + orderItem.getSubTotal());

            // Trả kết quả về cho người dùng (hoặc chuyển hướng)
            resp.getWriter().println("Success: " + beverage.getDescription());
            
        } catch (Exception e) {
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}
