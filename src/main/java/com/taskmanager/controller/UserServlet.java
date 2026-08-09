package com.taskmanager.controller;

import com.taskmanager.dao.UserDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.taskmanager.model.User;
import com.taskmanager.util.InputValidator;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();

        if (action == null || action.isBlank()) {
            List<User> list = dao.getAll();
            request.setAttribute("users", list);
            request.setAttribute("totalUsers", dao.countUsers());
            request.setAttribute("totalAdmins", dao.countByRole("admin"));
            request.setAttribute("totalMembers", dao.countByRole("user"));
            request.getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);

        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/users/form-add.jsp").forward(request, response);

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = dao.getById(id);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/views/users/form-edit.jsp").forward(request, response);
            }

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = dao.getById(id);

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/views/users/confirm-delete.jsp").forward(request, response);
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();

        if (action == null || action.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/users");

        } else if ("insert".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String role = request.getParameter("role");

            String validationError = InputValidator.validateUser(username, password, fullName, email, role, true);
            if (validationError != null) {
                showAddError(request, response, validationError, username, fullName, email, role);
                return;
            }

            boolean ok = dao.insert(username, password, fullName, email, role);

            if (ok) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                showAddError(request, response, "Khong the tao user. Vui long kiem tra du lieu.",
                        username, fullName, email, role);
            }

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String role = request.getParameter("role");

            String validationError = InputValidator.validateUser(username, password, fullName, email, role, false);
            if (validationError != null) {
                request.setAttribute("error", validationError);
                request.setAttribute("user", new User(id, username, fullName, email, role));
                request.getRequestDispatcher("/WEB-INF/views/users/form-edit.jsp").forward(request, response);
                return;
            }

            boolean ok = dao.update(id, username, password, fullName, email, role);

            if (ok) {
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                request.setAttribute("error", "Khong the cap nhat user.");
                User user = dao.getById(id);
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/views/users/form-edit.jsp").forward(request, response);
            }

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
            response.sendRedirect(request.getContextPath() + "/users");

        } else {
            response.sendRedirect(request.getContextPath() + "/users");
        }
    }

    private void showAddError(HttpServletRequest request, HttpServletResponse response, String error,
            String username, String fullName, String email, String role) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("oldUsername", username);
        request.setAttribute("oldFullName", fullName);
        request.setAttribute("oldEmail", email);
        request.setAttribute("oldRole", role);
        request.getRequestDispatcher("/WEB-INF/views/users/form-add.jsp").forward(request, response);
    }

  

}
