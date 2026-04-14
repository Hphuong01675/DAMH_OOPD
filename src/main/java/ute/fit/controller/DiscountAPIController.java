package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.fit.model.Order;
import ute.fit.model.OrderItem;
import ute.fit.service.IDiscountService;
import ute.fit.service.impl.DiscountServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/calculate-discount")
public class DiscountAPIController extends HttpServlet {
    private final IDiscountService discountService = new DiscountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

     // Thay thế đoạn logic tính toán cũ trong doGet:
        try {
            String promoCode = req.getParameter("code");
            String totalStr = req.getParameter("total");

            if (totalStr == null || totalStr.isEmpty() || "NONE".equals(promoCode)) {
                out.print("{\"discountAmount\": 0}");
                return;
            }

            double originalPrice = Double.parseDouble(totalStr.replaceAll("[^0-9.]", ""));

            // Sử dụng trực tiếp hàm calculateDiscountAmount của Service
            // Hàm này trả về đúng số tiền ĐƯỢC GIẢM (ví dụ: 3.700)
            double discountAmountForUI = discountService.calculateDiscountAmount(originalPrice, promoCode);
            
            // Đảm bảo an toàn dữ liệu
            discountAmountForUI = Math.max(0, Math.min(discountAmountForUI, originalPrice));

            out.print("{\"discountAmount\": " + discountAmountForUI + "}");

        } catch (Exception e) {
            e.printStackTrace(); 
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Server Error\"}");
        } finally {
            out.flush();
        }
    }
}