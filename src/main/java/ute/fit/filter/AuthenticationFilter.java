package ute.fit.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/staff/*", "/barista/*"})
public class AuthenticationFilter extends HttpFilter implements Filter {

    private static final long serialVersionUID = 1L;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        AccountEntity account = session == null ? null : (AccountEntity) session.getAttribute("loggedInUser");

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (!isAuthorized(req.getServletPath(), account.getRole())) {
            resp.sendRedirect(req.getContextPath() + resolveTarget(account.getRole()));
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isAuthorized(String servletPath, Roles role) {
        if (servletPath.startsWith("/admin/")) {
            return role == Roles.Admin;
        }
        if (servletPath.startsWith("/barista/")) {
            return role == Roles.Barista;
        }
        if (servletPath.startsWith("/staff/")) {
            return role == Roles.Staff;
        }
        return false;
    }

    private String resolveTarget(Roles role) {
        if (role == Roles.Admin) {
            return "/admin/dashboard";
        }
        if (role == Roles.Barista) {
            return "/barista/dashboard";
        }
        return "/staff/dashboard";
    }
}
