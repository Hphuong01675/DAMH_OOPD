package ute.fit.controller;

import ute.fit.model.*;
import ute.fit.service.IBeverageService;
import ute.fit.service.impl.BeverageServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/topping")
public class ToppingController extends HttpServlet {

    @Override
    public void init() {

        ToppingFactory.register(new PearlCreator());
        ToppingFactory.register(new CheeseFoamCreator());
        ToppingFactory.register(new CaramelCreator());
        ToppingFactory.register(new JellyCreator());
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	int productID = Integer.parseInt(req.getParameter("productID"));

    	IBeverageService beverageService = new BeverageServiceImpl();
    	BeverageDTO b = beverageService.getById(productID);
    	double price = b.getBasePrice();

    	List<ToppingDTO> toppings = ToppingFactory.getAllToppings();

    	req.setAttribute("basePrice", price);
    	req.setAttribute("toppings", toppings);

    	req.getRequestDispatcher("/WEB-INF/views/staff/topping.jsp")
    	   .forward(req, resp);
    }

//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        List<ToppingDTO> toppings = ToppingFactory.getAllToppings();
//
//        req.setAttribute("toppings", toppings);
//
//        req.getRequestDispatcher("/WEB-INF/views/staff/topping.jsp")
//                .forward(req, resp);
//    }
}