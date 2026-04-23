package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Users;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(contextPath.length());

        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Users u = null;
        if (session != null) {
            u = (Users) session.getAttribute("loggedInUser");
        }

        if (u == null) {
            res.sendRedirect(contextPath + "/login");
            return;
        }

        boolean admin = isAdmin(u);
        if (!admin && (path.startsWith("/users") || path.startsWith("/priorities") || path.startsWith("/priorites"))) {
            res.sendRedirect(contextPath + "/tasks");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return "/".equals(path)
                || "/index.html".equals(path)
                || path.startsWith("/login")
                || path.startsWith("/assets/")
                || path.startsWith("/favicon");
    }

    private boolean isAdmin(Users u) {
        if (u == null || u.getRole() == null) {
            return false;
        }
        return "admin".equalsIgnoreCase(u.getRole());
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
