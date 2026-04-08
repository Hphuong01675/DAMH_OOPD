package ute.fit.controller;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ute.fit.model.*;
import ute.fit.model.state.PendingState;
import ute.fit.service.IBeverageService;
import ute.fit.service.IToppingService;
import ute.fit.service.ProductService;
import ute.fit.service.impl.BeverageServiceImpl;
import ute.fit.service.impl.ToppingServiceImpl;

@WebServlet("/buildDrink")
public class BuildDrinkController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		IToppingService toppingService = new ToppingServiceImpl();
	    HttpSession session = request.getSession();

	    // ===== Lấy order =====
	    Order order = (Order) session.getAttribute("order");
	    if (order == null || !(order.getCurrentState() instanceof PendingState)) {
	        order = new Order();
	    }

	    try {
	        // ===== Lấy ID từ JSP =====
	        int beverageId = Integer.parseInt(request.getParameter("productID")); // FIX tên param

	        // ===== Lấy từ DB =====
	        IBeverageService beverageService = new BeverageServiceImpl();
	        BeverageDTO beverageDTO = beverageService.getById(beverageId);

	        if (beverageDTO == null) {
	            response.sendRedirect("order");
	            return;
	        }
	         

	        // ===== Build Beverage =====
	        Product product = beverageService.getBeverageBuilder(beverageDTO)
                    .setSugar(SugarLevel.valueOf(request.getParameter("sugar")))
                    .setSize(Size.valueOf(request.getParameter("size")))
                    .setIce(IceLevel.valueOf(request.getParameter("ice")))
                    .build();
	        System.out.println("===== DEBUG START =====");

	        System.out.println("productID: " + request.getParameter("productID"));
	        System.out.println("quantity: " + request.getParameter("quantity"));
	        System.out.println("size: " + request.getParameter("size"));
	        System.out.println("sugar: " + request.getParameter("sugar"));
	        System.out.println("ice: " + request.getParameter("ice"));

	        // ===== APPLY TOPPING =====
	        String[] toppingNames = request.getParameterValues("toppingNames");
	        String[] toppingQtys = request.getParameterValues("toppingQtys");

	        if (toppingNames != null && toppingQtys != null) {
	            for (int i = 0; i < toppingNames.length; i++) {

	                int qty = Integer.parseInt(toppingQtys[i]);

	                for (int j = 0; j < qty; j++) {
	                    product = ToppingFactory.createTopping(toppingNames[i], product);
	                }
	            }
	        }

	        // ===== Quantity =====
	        int quantity = Integer.parseInt(request.getParameter("quantity"));

	        // ===== Tạo item =====
	        OrderItem item = new OrderItem(product, quantity);

	        // ===== Add vào order =====
	        order.addItem(item);

	        // ===== Lưu session =====
	        session.setAttribute("order", order);

	    } catch (Exception e) {
	        e.printStackTrace(); // debug
	    }

	    // ===== Redirect chuẩn MVC =====
	    response.sendRedirect("order"); // ✅ FIX QUAN TRỌNG
	}
	
}