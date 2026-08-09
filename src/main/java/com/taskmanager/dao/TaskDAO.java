package com.taskmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.taskmanager.config.DatabaseConnectionProvider;
import com.taskmanager.model.Priority;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.util.InputValidator;

public class TaskDAO extends DatabaseConnectionProvider {

    public List<Task> getList() {
        String sql = "SELECT tasks.id, tasks.task_name, users.user_name, priorities.priority_name, priorities.color_code, tasks.due_date, tasks.status "
                + "FROM priorities "
                + "INNER JOIN tasks ON priorities.id = tasks.priority_id "
                + "INNER JOIN users ON tasks.user_id = users.id "
                + "ORDER BY tasks.id DESC";

        List<Task> list = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User(
                        null,
                        null,
                        rs.getString("user_name"),
                        null,
                        null
                );
                Priority p = new Priority(
                        null,
                        rs.getString("priority_name"),
                        InputValidator.safeColor(rs.getString("color_code"))
                );
                Task t = new Task(
                        rs.getInt("id"),
                        u,
                        rs.getString("task_name"),
                        p,
                        rs.getString("due_date"),
                        rs.getString("status")
                );
                list.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public Task getById(int id) {
        String sql = "SELECT t.id, t.task_name, t.user_id, u.user_name, t.priority_id, p.priority_name, p.color_code, t.due_date, t.status "
                + "FROM tasks t "
                + "INNER JOIN users u ON t.user_id = u.id "
                + "INNER JOIN priorities p ON t.priority_id = p.id "
                + "WHERE t.id = ?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User(
                            rs.getInt("user_id"),
                            null,
                            rs.getString("user_name"),
                            null,
                            null
                    );
                    Priority p = new Priority(
                            rs.getInt("priority_id"),
                            rs.getString("priority_name"),
                            InputValidator.safeColor(rs.getString("color_code"))
                    );
                    return new Task(
                            rs.getInt("id"),
                            u,
                            rs.getString("task_name"),
                            p,
                            rs.getString("due_date"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public List<Task> getListByUserId(int userId) {
        String sql = "SELECT tasks.id, tasks.task_name, users.user_name, priorities.priority_name, priorities.color_code, tasks.due_date, tasks.status "
                + "FROM priorities "
                + "INNER JOIN tasks ON priorities.id = tasks.priority_id "
                + "INNER JOIN users ON tasks.user_id = users.id "
                + "WHERE tasks.user_id = ? "
                + "ORDER BY tasks.id DESC";

        List<Task> list = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User(
                            userId,
                            null,
                            rs.getString("user_name"),
                            null,
                            null
                    );
                    Priority p = new Priority(
                            null,
                            rs.getString("priority_name"),
                            InputValidator.safeColor(rs.getString("color_code"))
                    );
                    Task t = new Task(
                            rs.getInt("id"),
                            u,
                            rs.getString("task_name"),
                            p,
                            rs.getString("due_date"),
                            rs.getString("status")
                    );
                    list.add(t);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Task getByIdAndUserId(int id, int userId) {
        String sql = "SELECT t.id, t.task_name, t.user_id, u.user_name, t.priority_id, p.priority_name, p.color_code, t.due_date, t.status "
                + "FROM tasks t "
                + "INNER JOIN users u ON t.user_id = u.id "
                + "INNER JOIN priorities p ON t.priority_id = p.id "
                + "WHERE t.id = ? AND t.user_id = ?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User(
                            rs.getInt("user_id"),
                            null,
                            rs.getString("user_name"),
                            null,
                            null
                    );
                    Priority p = new Priority(
                            rs.getInt("priority_id"),
                            rs.getString("priority_name"),
                            InputValidator.safeColor(rs.getString("color_code"))
                    );
                    return new Task(
                            rs.getInt("id"),
                            u,
                            rs.getString("task_name"),
                            p,
                            rs.getString("due_date"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Boolean insert(String taskName, int assigneeId, int priorityId, String deadline, String status) {
        String sql = "INSERT INTO tasks (task_name, user_id, priority_id, due_date, status) VALUES (?, ?, ?, ?, ?)";
        return executeUpsert(sql, null, taskName, assigneeId, priorityId, deadline, status);
    }

    public Boolean update(int id, String taskName, int assigneeId, int priorityId, String deadline, String status) {
        String sql = "UPDATE tasks SET task_name = ?, user_id = ?, priority_id = ?, due_date = ?, status = ? WHERE id = ?";
        return executeUpsert(sql, id, taskName, assigneeId, priorityId, deadline, status);
    }

    public Boolean delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private Boolean executeUpsert(String sql, Integer id, String taskName, int assigneeId, int priorityId, String deadline, String status) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            fillBaseParams(ps, id, taskName, assigneeId, priorityId, deadline);
            ps.setString(5, status);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException firstEx) {
            // Tương thích database phiên bản cũ từng lưu status bằng kiểu BIT.
            try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
                fillBaseParams(ps, id, taskName, assigneeId, priorityId, deadline);
                ps.setBoolean(5, isDoneStatus(status));
                int row = ps.executeUpdate();
                return row > 0;
            } catch (SQLException secondEx) {
                Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, firstEx);
                Logger.getLogger(TaskDAO.class.getName()).log(Level.SEVERE, null, secondEx);
            }
        }
        return false;
    }

    private void fillBaseParams(PreparedStatement ps, Integer id, String taskName, int assigneeId, int priorityId, String deadline) throws SQLException {
        ps.setString(1, taskName);
        ps.setInt(2, assigneeId);
        ps.setInt(3, priorityId);
        if (deadline == null || deadline.isBlank()) {
            ps.setNull(4, java.sql.Types.DATE);
        } else {
            ps.setDate(4, java.sql.Date.valueOf(deadline));
        }
        if (id != null) {
            ps.setInt(6, id);
        }
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
