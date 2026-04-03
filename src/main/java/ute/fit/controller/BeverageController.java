package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import ute.fit.entity.BeverageEntity;
import ute.fit.service.IBeverageService;
import ute.fit.service.impl.BeverageServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/beverages", "/admin/beverage/save", "/admin/beverage/delete", "/admin/beverage/edit"})
@MultipartConfig
public class BeverageController extends HttpServlet {
    private IBeverageService service = new BeverageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/admin/beverages")) {
            req.setAttribute("list", service.getAll());
            req.getRequestDispatcher("/views/admin/beverage-list.jsp").forward(req, resp);
        } else if (path.equals("/admin/beverage/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("item", service.getById(id));
            req.setAttribute("list", service.getAll());
            req.getRequestDispatcher("/views/admin/beverage-list.jsp").forward(req, resp);
        } else if (path.equals("/admin/beverage/delete")) {
            service.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/admin/beverages");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BeverageEntity bev = new BeverageEntity();
            String idStr = req.getParameter("productID");
            if (idStr != null && !idStr.isEmpty()) bev.setProductID(Integer.parseInt(idStr));
            
            bev.setName(req.getParameter("name"));
            bev.setBasePrice(Double.parseDouble(req.getParameter("basePrice")));
            
            service.save(bev, req.getPart("image"));
            resp.sendRedirect(req.getContextPath() + "/admin/beverages");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }
}