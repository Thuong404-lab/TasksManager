package controller;

import dao.UsersDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;

@WebServlet(name = "UsersServlet", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        UsersDAO dao = new UsersDAO();

        if (action == null || action.isBlank()) {
            List<Users> list = dao.getAll();
            request.setAttribute("users", list);
            request.setAttribute("totalUsers", dao.countUsers());
            request.setAttribute("totalAdmins", dao.countByRole("admin"));
            request.setAttribute("totalMembers", dao.countByRole("user"));
            request.getRequestDispatcher("/WEB-INF/user/users.jsp").forward(request, response);

        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/user/add.jsp").forward(request, response);

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Users user = dao.getById(id);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/user/edit.jsp").forward(request, response);
            }

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Users user = dao.getById(id);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/user/delete.jsp").forward(request, response);
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        UsersDAO dao = new UsersDAO();

        if (action == null || action.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/users");

        } else if ("insert".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String role = request.getParameter("role");

            boolean ok = dao.insert(username, password, fullName, email, role);

            if (ok) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                request.setAttribute("error", "Khong the tao user. Vui long kiem tra du lieu.");
                request.setAttribute("oldUsername", username);
                request.setAttribute("oldFullName", fullName);
                request.setAttribute("oldEmail", email);
                request.setAttribute("oldRole", role);
                request.getRequestDispatcher("/WEB-INF/user/add.jsp").forward(request, response);
            }

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String role = request.getParameter("role");

            boolean ok = dao.update(id, username, password, fullName, email, role);

            if (ok) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                request.setAttribute("error", "Khong the cap nhat user.");
                Users user = dao.getById(id);
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/user/edit.jsp").forward(request, response);
            }

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
            response.sendRedirect(request.getContextPath() + "/users");

        } else {
            response.sendRedirect(request.getContextPath() + "/users");
        }
    }

  

    @Override
    public String getServletInfo() {
        return "Users servlet";
    }
}
