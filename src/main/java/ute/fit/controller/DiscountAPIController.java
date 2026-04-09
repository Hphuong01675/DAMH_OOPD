package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.fit.service.IDiscountService;
import ute.fit.service.impl.DiscountServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/calculate-discount")

public class DiscountAPIController extends HttpServlet {
    private final IDiscountService discountService = new DiscountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            String promoCode = req.getParameter("code");
            String totalStr = req.getParameter("total");

            // Kiểm tra đầu vào cơ bản
            if (promoCode == null || totalStr == null || totalStr.isEmpty() || "NONE".equals(promoCode)) {
                out.print("{\"discountAmount\": 0}");
                return;
            }

            // Parse số an toàn
            double originalPrice = Double.parseDouble(totalStr.replaceAll("[^0-9.]", ""));

            // Gọi Service thực thi Strategy Pattern
            double priceAfterDiscount = discountService.getPriceWithVoucher(originalPrice, promoCode);
            
            // Trả về số tiền được giảm (giá gốc - giá sau giảm)
            double discountAmount = originalPrice - priceAfterDiscount;

            // Đảm bảo số tiền giảm không bị âm
            discountAmount = Math.max(0, discountAmount);

            out.print("{\"discountAmount\": " + discountAmount + "}");

        } catch (Exception e) {
            e.printStackTrace(); // Xem lỗi tại console server
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Server Error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}