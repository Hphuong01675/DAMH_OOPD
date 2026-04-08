package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import ute.fit.model.BeverageDTO;
import ute.fit.service.IBeverageService;
import ute.fit.service.IToppingService;
import ute.fit.service.impl.BeverageServiceImpl;
import ute.fit.service.impl.ToppingServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {
    "/admin/products", 
    "/admin/product/save", 
    "/admin/product/toggle", 
    "/admin/product/edit"
})
@MultipartConfig
public class ProductController extends HttpServlet {
    private IBeverageService beverageService = new BeverageServiceImpl();
    private IToppingService toppingService = new ToppingServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String keyword = req.getParameter("keyword");

        if (path.equals("/admin/product/toggle")) {
            int id = Integer.parseInt(req.getParameter("id"));
            beverageService.toggleStatus(id);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        if (path.equals("/admin/product/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("item", beverageService.getById(id));
        }

        req.setAttribute("list", beverageService.getAllBeverages(keyword));
        req.setAttribute("toppings", toppingService.getAllToppings());

        req.getRequestDispatcher("/WEB-INF/views/admin/product-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Đảm bảo nhận dữ liệu tiếng Việt từ form
        try {
            BeverageDTO dto = new BeverageDTO();

            String idStr = req.getParameter("productID");
            if (idStr != null && !idStr.isEmpty()) {
                dto.setProductID(Integer.parseInt(idStr));
            }

            dto.setName(req.getParameter("name"));
            dto.setBasePrice(Double.parseDouble(req.getParameter("basePrice")));

            beverageService.save(dto, req.getPart("image"));

            resp.sendRedirect(req.getContextPath() + "/admin/products");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }
}
