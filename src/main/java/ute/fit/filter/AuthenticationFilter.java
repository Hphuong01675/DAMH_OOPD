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
import ute.fit.model.Roles;
import ute.fit.model.UserDTO;

import java.io.IOException;

@WebFilter(urlPatterns = { "/admin/*", "/staff/*", "/barista/*" })
public class AuthenticationFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		UserDTO user = (session != null) ? (UserDTO) session.getAttribute("user") : null;

		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String path = req.getServletPath();
		String role = user.getRole();

		// Kiểm tra quyền an toàn hơn bằng chuỗi
		boolean authorized = false;
		if (path.startsWith("/admin") && "Admin".equalsIgnoreCase(role))
			authorized = true;
		else if (path.startsWith("/barista") && "Barista".equalsIgnoreCase(role))
			authorized = true;
		else if (path.startsWith("/staff") && "Staff".equalsIgnoreCase(role))
			authorized = true;

		if (authorized) {
			chain.doFilter(request, response);
		} else {
			// Gọi resolveTarget thay vì resolveTargetByRole
			resp.sendRedirect(req.getContextPath() + resolveTarget(role));
		}
	}

	private boolean isAuthorized(String path, String role) {
		if (path.startsWith("/admin") && "Admin".equalsIgnoreCase(role))
			return true;
		if (path.startsWith("/barista") && "Barista".equalsIgnoreCase(role))
			return true;
		if (path.startsWith("/staff") && "Staff".equalsIgnoreCase(role))
			return true;
		return false;
	}

	private String resolveTarget(String role) {
		if ("Admin".equalsIgnoreCase(role)) {
			return "/admin/dashboard";
		}
		if ("Barista".equalsIgnoreCase(role)) {
			return "/barista/dashboard";
		}
		return "/staff/dashboard";
	}
}
