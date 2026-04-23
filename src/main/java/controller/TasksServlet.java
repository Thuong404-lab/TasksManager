 package controller;

import dao.PrioritesDAO;
import dao.TasksDAO;
import dao.UsersDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Tasks;
import model.Users;

@WebServlet(name = "TasksServlet", urlPatterns = {"/tasks"})
public class TasksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        if (view == null || view.isBlank()) {
            view = "list";
        }

        Users currentUser = getCurrentUser(request);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        boolean admin = isAdmin(currentUser);

        TasksDAO tasksDAO = new TasksDAO();
        UsersDAO usersDAO = new UsersDAO();
        PrioritesDAO prioritesDAO = new PrioritesDAO();

        if ("list".equals(view)) {
            List<Tasks> list = admin
                    ? tasksDAO.getList()
                    : tasksDAO.getListByUserId(currentUser.getId());

            request.setAttribute("tasks", list);
            request.setAttribute("isAdmin", admin);
            request.setAttribute("totalTasks", list.size());
            request.setAttribute("completedTasks", countCompleted(list));
            request.setAttribute("upcomingTasks", countUpcoming(list));
            request.getRequestDispatcher("/WEB-INF/view/tasks.jsp").forward(request, response);

        } else if ("add".equals(view)) {
            request.setAttribute("isAdmin", admin);
            request.setAttribute("priorities", prioritesDAO.getList());

            if (admin) {
                request.setAttribute("users", usersDAO.getList());
            } else {
                List<Users> onlyMe = new ArrayList<>();
                onlyMe.add(currentUser);
                request.setAttribute("users", onlyMe);
            }

            request.getRequestDispatcher("/WEB-INF/view/add.jsp").forward(request, response);

        } else if ("edit".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Tasks task = admin ? tasksDAO.getById(id) : tasksDAO.getByIdAndUserId(id, currentUser.getId());

            if (task == null) {
                response.sendRedirect(request.getContextPath() + "/tasks");
                return;
            }

            request.setAttribute("isAdmin", admin);
            request.setAttribute("task", task);
            request.setAttribute("priorities", prioritesDAO.getList());

            if (admin) {
                request.setAttribute("users", usersDAO.getList());
            } else {
                List<Users> onlyMe = new ArrayList<>();
                onlyMe.add(currentUser);
                request.setAttribute("users", onlyMe);
            }

            request.getRequestDispatcher("/WEB-INF/view/edit.jsp").forward(request, response);

        } else if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Tasks task = admin ? tasksDAO.getById(id) : tasksDAO.getByIdAndUserId(id, currentUser.getId());

            if (task == null) {
                response.sendRedirect(request.getContextPath() + "/tasks");
                return;
            }

            request.setAttribute("task", task);
            request.getRequestDispatcher("/WEB-INF/view/delete.jsp").forward(request, response);

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

        Users currentUser = getCurrentUser(request);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        boolean admin = isAdmin(currentUser);

        TasksDAO tasksDAO = new TasksDAO();
        UsersDAO usersDAO = new UsersDAO();
        PrioritesDAO prioritesDAO = new PrioritesDAO();

        if ("add".equals(view)) {
            String taskName = request.getParameter("name");
            String assignee = request.getParameter("assignee");
            String priority = request.getParameter("priority");
            String deadline = request.getParameter("deadline");
            String status = request.getParameter("status");

            try {
                int assigneeId = admin ? Integer.parseInt(assignee) : currentUser.getId();
                int priorityId = Integer.parseInt(priority);

                Boolean inserted = tasksDAO.insert(taskName, assigneeId, priorityId, deadline, status);

                if (Boolean.TRUE.equals(inserted)) {
                    response.sendRedirect(request.getContextPath() + "/tasks");
                } else {
                    request.setAttribute("error", "Khong the tao task. Vui long kiem tra du lieu.");
                    request.setAttribute("isAdmin", admin);
                    request.setAttribute("priorities", prioritesDAO.getList());

                    if (admin) {
                        request.setAttribute("users", usersDAO.getList());
                    } else {
                        List<Users> onlyMe = new ArrayList<>();
                        onlyMe.add(currentUser);
                        request.setAttribute("users", onlyMe);
                    }

                    request.getRequestDispatcher("/WEB-INF/view/add.jsp").forward(request, response);
                }

            } catch (NumberFormatException ex) {
                request.setAttribute("error", "Nguoi phu trach hoac muc uu tien khong hop le.");
                request.setAttribute("isAdmin", admin);
                request.setAttribute("priorities", prioritesDAO.getList());

                if (admin) {
                    request.setAttribute("users", usersDAO.getList());
                } else {
                    List<Users> onlyMe = new ArrayList<>();
                    onlyMe.add(currentUser);
                    request.setAttribute("users", onlyMe);
                }

                request.getRequestDispatcher("/WEB-INF/view/add.jsp").forward(request, response);
            }

        } else if ("edit".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Tasks task = admin ? tasksDAO.getById(id) : tasksDAO.getByIdAndUserId(id, currentUser.getId());

            if (task == null) {
                response.sendRedirect(request.getContextPath() + "/tasks");
                return;
            }

            String taskName = request.getParameter("name");
            String assignee = request.getParameter("assignee");
            String priority = request.getParameter("priority");
            String deadline = request.getParameter("deadline");
            String status = request.getParameter("status");

            try {
                int assigneeId = admin ? Integer.parseInt(assignee) : currentUser.getId();
                int priorityId = Integer.parseInt(priority);

                Boolean updated = tasksDAO.update(id, taskName, assigneeId, priorityId, deadline, status);

                if (Boolean.TRUE.equals(updated)) {
                    response.sendRedirect(request.getContextPath() + "/tasks");
                } else {
                    request.setAttribute("error", "Khong the cap nhat task.");
                    request.setAttribute("isAdmin", admin);
                    request.setAttribute("task", task);
                    request.setAttribute("priorities", prioritesDAO.getList());

                    if (admin) {
                        request.setAttribute("users", usersDAO.getList());
                    } else {
                        List<Users> onlyMe = new ArrayList<>();
                        onlyMe.add(currentUser);
                        request.setAttribute("users", onlyMe);
                    }

                    request.getRequestDispatcher("/WEB-INF/view/edit.jsp").forward(request, response);
                }

            } catch (NumberFormatException ex) {
                request.setAttribute("error", "Nguoi phu trach hoac muc uu tien khong hop le.");
                request.setAttribute("isAdmin", admin);
                request.setAttribute("task", task);
                request.setAttribute("priorities", prioritesDAO.getList());

                if (admin) {
                    request.setAttribute("users", usersDAO.getList());
                } else {
                    List<Users> onlyMe = new ArrayList<>();
                    onlyMe.add(currentUser);
                    request.setAttribute("users", onlyMe);
                }

                request.getRequestDispatcher("/WEB-INF/view/edit.jsp").forward(request, response);
            }

        } else if ("delete".equals(view)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Tasks task = admin ? tasksDAO.getById(id) : tasksDAO.getByIdAndUserId(id, currentUser.getId());

            if (task != null) {
                tasksDAO.delete(id);
            }

            response.sendRedirect(request.getContextPath() + "/tasks");

        } else {
            response.sendRedirect(request.getContextPath() + "/tasks");
        }
    }

    private Users getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Users) session.getAttribute("loggedInUser");
    }

    private boolean isAdmin(Users u) {
        return u != null && u.getRole() != null && "admin".equalsIgnoreCase(u.getRole());
    }

    private int countCompleted(List<Tasks> list) {
        int count = 0;
        for (Tasks t : list) {
            if (isDoneStatus(t.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private int countUpcoming(List<Tasks> list) {
        int count = 0;
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(3);

        for (Tasks t : list) {
            if (isDoneStatus(t.getStatus())) {
                continue;
            }

            String due = t.getDueDate();
            if (due == null || due.isBlank()) {
                continue;
            }
            // cái này chỉnh nếu còn 3d thì nó sẽ thông báp là dealline
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

    @Override
    public String getServletInfo() {
        return "Task servlet";
    }
}