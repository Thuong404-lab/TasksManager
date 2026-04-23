package controller;

import dao.UsersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Users;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final String SAVE_COOKIE = "rememberUser";
    private static final int TIME_COOKIE = 7 * 24 * 60 * 60;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rememberedAccount = getCookieValue(request, SAVE_COOKIE);
        if (rememberedAccount != null && !rememberedAccount.isBlank()) {
            request.setAttribute("rememberedAccount", rememberedAccount);
            request.setAttribute("rememberChecked", true);
        }

        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("name");
        String password = request.getParameter("pass");
        String remember = request.getParameter("remember");

        UsersDAO dao = new UsersDAO();
        Users user = dao.login(username, password);

        if (user == null) {
            request.setAttribute("error", "Sai tai khoan hoac mat khau!");
            request.setAttribute("rememberedAccount", username);
            request.setAttribute("rememberChecked", "on".equals(remember));
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(30 * 60);
        session.setAttribute("loggedInUser", user);

        Cookie rememberUserCookie = new Cookie(SAVE_COOKIE, "on".equals(remember) ? user.getUserAccount() : "");
        rememberUserCookie.setHttpOnly(true);
        rememberUserCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        rememberUserCookie.setMaxAge("on".equals(remember) ? TIME_COOKIE : 0);
        response.addCookie(rememberUserCookie);

        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
