package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import ute.fit.entity.BeverageEntity;
import ute.fit.service.IBeverageService;
import ute.fit.service.IToppingService;
import ute.fit.service.impl.BeverageServiceImpl;
import ute.fit.service.impl.ToppingServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
   "/admin/products", 
    "/admin/product/save", 
    "/admin/product/delete", 
   "/admin/product/edit"
})
//@MultipartConfig
public class ProductController extends HttpServlet {
    private IBeverageService serviceBeverage = new BeverageServiceImpl();
    private IToppingService serviceTopping = new ToppingServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String keyword = req.getParameter("keyword");

        if (path.equals("/admin/product/delete")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                serviceBeverage.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return; 
        }

        if (path.equals("/admin/product/edit")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("item", serviceBeverage.getById(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Logic chung cho hiển thị danh sách (cả trang chủ products, search và sau khi nhấn edit)
        // Nạp danh sách Beverage (xử lý tìm kiếm tập trung ở service)
        req.setAttribute("list", serviceBeverage.getAllBeverages(keyword));
        
        // Nạp danh sách Topping từ DB cho phần cuối trang
        req.setAttribute("toppings", serviceTopping.getAllToppings());

        req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Đảm bảo nhận dữ liệu tiếng Việt từ form
        try {
            BeverageEntity bev = new BeverageEntity();
            String idStr = req.getParameter("productID");
            if (idStr != null && !idStr.isEmpty()) {
                bev.setProductID(Integer.parseInt(idStr));
            }
            
            bev.setName(req.getParameter("name"));
            bev.setBasePrice(Double.parseDouble(req.getParameter("basePrice")));
            
            // Lưu sản phẩm (Service xử lý file ảnh)
            serviceBeverage.save(bev, req.getPart("image"));
            
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Lỗi xử lý dữ liệu sản phẩm");
        }
    }
}