package controller;

import dao.PrioritesDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Priorities;

@WebServlet(name = "PrioritesServlet", urlPatterns = {"/priorities", "/priorites"})
public class PrioritesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        PrioritesDAO dao = new PrioritesDAO();

        if (action == null || action.isBlank()) {
            List<Priorities> list = dao.getAll();
            request.setAttribute("priorities", list);
            request.getRequestDispatcher("/WEB-INF/priorities/priorities.jsp").forward(request, response);

        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/priorities/add.jsp").forward(request, response);

        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Priorities priority = dao.getById(id);

            if (priority == null) {
                response.sendRedirect(request.getContextPath() + "/priorities");
            } else {
                request.setAttribute("priority", priority);
                request.getRequestDispatcher("/WEB-INF/priorities/edit.jsp").forward(request, response);
            }

        } else if ("delete".equals(action)) {
            int id =Integer.parseInt(request.getParameter("id"));
            Priorities priority = dao.getById(id);

            if (priority == null) {
                response.sendRedirect(request.getContextPath() + "/priorities");
            } else {
                request.setAttribute("priority", priority);
                request.getRequestDispatcher("/WEB-INF/priorities/delete.jsp").forward(request, response);
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
            PrioritesDAO dao = new PrioritesDAO();

            if ("insert".equals(action)) {
                String priorityName = request.getParameter("priorityName");
                String colorCode = request.getParameter("colorCode");

                boolean ok = dao.insert(priorityName, colorCode);
                if (ok) {
                    response.sendRedirect(request.getContextPath() + "/priorities");
                } else {
                    request.setAttribute("error", "Khong the tao priority.");
                    request.setAttribute("oldPriorityName", priorityName);
                    request.setAttribute("oldColorCode", colorCode);
                    request.getRequestDispatcher("/WEB-INF/priorities/add.jsp").forward(request, response);
                }

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String priorityName = request.getParameter("priorityName");
                String colorCode = request.getParameter("colorCode");

                boolean ok = dao.update(id, priorityName, colorCode);
                if (ok) {
                    response.sendRedirect(request.getContextPath() + "/priorities");
                } else {
                    request.setAttribute("error", "Khong the cap nhat priority.");
                    request.setAttribute("priority", dao.getById(id));
                    request.getRequestDispatcher("/WEB-INF/priorities/edit.jsp").forward(request, response);
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

    @Override
    public String getServletInfo() {
        return "Priorities servlet";
    }
}
