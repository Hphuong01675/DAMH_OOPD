package ute.fit.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ute.fit.model.BeverageDTO;
import ute.fit.service.IBeverageService;
import ute.fit.service.impl.BeverageServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/order"})
public class OrderController extends HttpServlet {
    
    private IBeverageService beverageService = new BeverageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        List<BeverageDTO> list = beverageService.findAll();
        
        req.setAttribute("beverages", list);
        
        req.getRequestDispatcher("/WEB-INF/views/staff/order.jsp").forward(req, resp);
    }
}