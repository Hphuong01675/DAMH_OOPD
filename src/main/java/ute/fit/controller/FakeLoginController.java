package ute.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/fake-login")
public class FakeLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // Tạo Map giả lập khớp với cấu trúc EL trên JSP (${sessionScope.account.name})
        Map<String, Object> auth = new HashMap<>();
        auth.put("username", "barista01"); // Phải trùng username dưới CSDL
        auth.put("role", "BARISTA");
        auth.put("name", "Nguyễn Văn Pha Chế"); 
        
        // Bơm vào session
        session.setAttribute("account", auth);
        
        // Chuyển hướng sang Dashboard
        response.sendRedirect(request.getContextPath() + "/barista/dashboard");
    }
}