package com.taskmanager.controller;

import com.taskmanager.dao.PriorityDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.taskmanager.model.Priority;
import com.taskmanager.util.InputValidator;

@WebServlet(name = "PriorityServlet", urlPatterns = "/priorities")
public class PriorityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        PriorityDAO dao = new PriorityDAO();

        if (action == null || action.isBlank()) {
            List<Priority> list = dao.getAll();
            request.setAttribute("priorities", list);
            request.getRequestDispatcher("/WEB-INF/views/priorities/list.jsp").forward(request, response);

        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/views/priorities/form-add.jsp").forward(request, response);

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Priority priority = dao.getById(id);

            if (priority == null) {
                response.sendRedirect(request.getContextPath() + "/priorities");
            } else {
                request.setAttribute("priority", priority);
                request.getRequestDispatcher("/WEB-INF/views/priorities/form-edit.jsp").forward(request, response);
            }

        } else if ("delete".equals(action)) {
            int id =Integer.parseInt(request.getParameter("id"));
            Priority priority = dao.getById(id);

            if (priority == null) {
                response.sendRedirect(request.getContextPath() + "/priorities");
            } else {
                request.setAttribute("priority", priority);
                request.getRequestDispatcher("/WEB-INF/views/priorities/confirm-delete.jsp").forward(request, response);
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/priorities");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/priorities");
        } else {
            PriorityDAO dao = new PriorityDAO();

            if ("insert".equals(action)) {
                String priorityName = request.getParameter("priorityName");
                String colorCode = request.getParameter("colorCode");

                String validationError = InputValidator.validatePriority(priorityName, colorCode);
                if (validationError != null) {
                    request.setAttribute("error", validationError);
                    request.setAttribute("oldPriorityName", priorityName);
                    request.setAttribute("oldColorCode", colorCode);
                    request.getRequestDispatcher("/WEB-INF/views/priorities/form-add.jsp").forward(request, response);
                    return;
                }

                boolean ok = dao.insert(priorityName, colorCode);
                if (ok) {
                    response.sendRedirect(request.getContextPath() + "/priorities");
                } else {
                    request.setAttribute("error", "Khong the tao priority.");
                    request.setAttribute("oldPriorityName", priorityName);
                    request.setAttribute("oldColorCode", colorCode);
                    request.getRequestDispatcher("/WEB-INF/views/priorities/form-add.jsp").forward(request, response);
                }

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String priorityName = request.getParameter("priorityName");
                String colorCode = request.getParameter("colorCode");

                String validationError = InputValidator.validatePriority(priorityName, colorCode);
                if (validationError != null) {
                    request.setAttribute("error", validationError);
                    request.setAttribute("priority", new Priority(id, priorityName, colorCode));
                    request.getRequestDispatcher("/WEB-INF/views/priorities/form-edit.jsp").forward(request, response);
                    return;
                }

                boolean ok = dao.update(id, priorityName, colorCode);
                if (ok) {
                    response.sendRedirect(request.getContextPath() + "/priorities");
                } else {
                    request.setAttribute("error", "Khong the cap nhat priority.");
                    request.setAttribute("priority", dao.getById(id));
                    request.getRequestDispatcher("/WEB-INF/views/priorities/form-edit.jsp").forward(request, response);
                }

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect(request.getContextPath() + "/priorities");

            } else {
                response.sendRedirect(request.getContextPath() + "/priorities");
            }
        }
    }

}
