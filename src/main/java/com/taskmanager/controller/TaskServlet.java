package com.taskmanager.controller;

import com.taskmanager.dao.PriorityDAO;
import com.taskmanager.dao.TaskDAO;
import com.taskmanager.dao.UserDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.util.InputValidator;

@WebServlet(name = "TaskServlet", urlPatterns = "/tasks")
public class TaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        if (view == null || view.isBlank()) {
            view = "list";
        }

        User currentUser = getCurrentUser(request);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        boolean admin = isAdmin(currentUser);

        TaskDAO tasksDAO = new TaskDAO();
        UserDAO usersDAO = new UserDAO();
        PriorityDAO prioritesDAO = new PriorityDAO();

        if ("list".equals(view)) {
            List<Task> list = admin
                    ? tasksDAO.getList()
                    : tasksDAO.getListByUserId(currentUser.getId());

            request.setAttribute("tasks", list);
            request.setAttribute("isAdmin", admin);
            request.setAttribute("totalTasks", list.size());
            request.setAttribute("completedTasks", countCompleted(list));
            request.setAttribute("upcomingTasks", countUpcoming(list));
            request.getRequestDispatcher("/WEB-INF/views/tasks/list.jsp").forward(request, response);

        } else if ("add".equals(view)) {
            showTaskForm(request, response, "/WEB-INF/views/tasks/form-add.jsp", null,
                    admin, currentUser, usersDAO, prioritesDAO);

        } else if ("edit".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Task task = admin ? tasksDAO.getById(id) : tasksDAO.getByIdAndUserId(id, currentUser.getId());

            if (task == null) {
                response.sendRedirect(request.getContextPath() + "/tasks");
                return;
            }

            request.setAttribute("task", task);
            showTaskForm(request, response, "/WEB-INF/views/tasks/form-edit.jsp", null,
                    admin, currentUser, usersDAO, prioritesDAO);

        } else if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Task task = admin ? tasksDAO.getById(id) : tasksDAO.getByIdAndUserId(id, currentUser.getId());

            if (task == null) {
                response.sendRedirect(request.getContextPath() + "/tasks");
                return;
            }

            request.setAttribute("task", task);
            request.getRequestDispatcher("/WEB-INF/views/tasks/confirm-delete.jsp").forward(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/tasks");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        if (view == null || view.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/tasks");
            return;
        }

        User currentUser = getCurrentUser(request);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        boolean admin = isAdmin(currentUser);

        TaskDAO tasksDAO = new TaskDAO();
        UserDAO usersDAO = new UserDAO();
        PriorityDAO prioritesDAO = new PriorityDAO();

        if ("add".equals(view)) {
            String taskName = request.getParameter("name");
            String assignee = request.getParameter("assignee");
            String priority = request.getParameter("priority");
            String deadline = request.getParameter("deadline");
            String status = request.getParameter("status");

            String validationError = InputValidator.validateTask(taskName, deadline, status);
            if (validationError != null) {
                showTaskForm(request, response, "/WEB-INF/views/tasks/form-add.jsp", validationError, admin, currentUser, usersDAO, prioritesDAO);
                return;
            }

            try {
                int assigneeId = admin ? Integer.parseInt(assignee) : currentUser.getId();
                int priorityId = Integer.parseInt(priority);

                Boolean inserted = tasksDAO.insert(taskName, assigneeId, priorityId, deadline, status);

                if (Boolean.TRUE.equals(inserted)) {
                    response.sendRedirect(request.getContextPath() + "/tasks");
                } else {
                    showTaskForm(request, response, "/WEB-INF/views/tasks/form-add.jsp",
                            "Khong the tao task. Vui long kiem tra du lieu.",
                            admin, currentUser, usersDAO, prioritesDAO);
                }

            } catch (NumberFormatException ex) {
                showTaskForm(request, response, "/WEB-INF/views/tasks/form-add.jsp",
                        "Nguoi phu trach hoac muc uu tien khong hop le.",
                        admin, currentUser, usersDAO, prioritesDAO);
            }

        } else if ("edit".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Task task = admin ? tasksDAO.getById(id) : tasksDAO.getByIdAndUserId(id, currentUser.getId());

            if (task == null) {
                response.sendRedirect(request.getContextPath() + "/tasks");
                return;
            }

            String taskName = request.getParameter("name");
            String assignee = request.getParameter("assignee");
            String priority = request.getParameter("priority");
            String deadline = request.getParameter("deadline");
            String status = request.getParameter("status");

            String validationError = InputValidator.validateTask(taskName, deadline, status);
            if (validationError != null) {
                request.setAttribute("task", task);
                showTaskForm(request, response, "/WEB-INF/views/tasks/form-edit.jsp", validationError, admin, currentUser, usersDAO, prioritesDAO);
                return;
            }

            try {
                int assigneeId = admin ? Integer.parseInt(assignee) : currentUser.getId();
                int priorityId = Integer.parseInt(priority);

                Boolean updated = tasksDAO.update(id, taskName, assigneeId, priorityId, deadline, status);

                if (Boolean.TRUE.equals(updated)) {
                    response.sendRedirect(request.getContextPath() + "/tasks");
                } else {
                    request.setAttribute("task", task);
                    showTaskForm(request, response, "/WEB-INF/views/tasks/form-edit.jsp", "Khong the cap nhat task.",
                            admin, currentUser, usersDAO, prioritesDAO);
                }

            } catch (NumberFormatException ex) {
                request.setAttribute("task", task);
                showTaskForm(request, response, "/WEB-INF/views/tasks/form-edit.jsp",
                        "Nguoi phu trach hoac muc uu tien khong hop le.",
                        admin, currentUser, usersDAO, prioritesDAO);
            }

        } else if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Task task = admin ? tasksDAO.getById(id) : tasksDAO.getByIdAndUserId(id, currentUser.getId());

            if (task != null) {
                tasksDAO.delete(id);
            }

            response.sendRedirect(request.getContextPath() + "/tasks");

        } else {
            response.sendRedirect(request.getContextPath() + "/tasks");
        }
    }

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("loggedInUser");
    }

    private void showTaskForm(HttpServletRequest request, HttpServletResponse response, String jsp, String error,
            boolean admin, User currentUser, UserDAO usersDAO, PriorityDAO prioritesDAO)
            throws ServletException, IOException {
        if (error != null) {
            request.setAttribute("error", error);
        }
        request.setAttribute("isAdmin", admin);
        request.setAttribute("priorities", prioritesDAO.getList());
        request.setAttribute("users", admin ? usersDAO.getList() : List.of(currentUser));
        request.getRequestDispatcher(jsp).forward(request, response);
    }

    private boolean isAdmin(User u) {
        return u != null && u.getRole() != null && "admin".equalsIgnoreCase(u.getRole());
    }

    private int countCompleted(List<Task> list) {
        int count = 0;
        for (Task t : list) {
            if (isDoneStatus(t.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private int countUpcoming(List<Task> list) {
        int count = 0;
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(3);

        for (Task t : list) {
            if (isDoneStatus(t.getStatus())) {
                continue;
            }

            String due = t.getDueDate();
            if (due == null || due.isBlank()) {
                continue;
            }
            // Chỉ thống kê task chưa hoàn thành và đến hạn trong ba ngày tới.
            try {
                LocalDate dueDate = LocalDate.parse(due);
                if (!dueDate.isBefore(today) && !dueDate.isAfter(end)) {
                    count++;
                }
            } catch (DateTimeParseException ex) {
            }
        }

        return count;
    }

    private boolean isDoneStatus(String status) {
        if (status == null) {
            return false;
        }

        String normalized = status.trim().toLowerCase();
        return "done".equals(normalized)
                || "true".equals(normalized)
                || "1".equals(normalized);
    }

}
