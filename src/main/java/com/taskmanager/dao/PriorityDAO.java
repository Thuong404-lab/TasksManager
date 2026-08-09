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
import com.taskmanager.util.InputValidator;

public class PriorityDAO extends DatabaseConnectionProvider {

    public Priority getById(int id) {
        String sql = "SELECT id, priority_name, color_code FROM priorities WHERE id=?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Priority(
                            rs.getInt("id"),
                            rs.getString("priority_name"),
                            InputValidator.safeColor(rs.getString("color_code"))
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PriorityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Priority> getAll() {
        return getList();
    }

    public boolean insert(String priorityName, String colorCode) {
        String sql = "INSERT INTO priorities (priority_name, color_code) VALUES (?, ?)";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, priorityName);
            ps.setString(2, colorCode);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PriorityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update(int id, String priorityName, String colorCode) {
        String sql = "UPDATE priorities SET priority_name=?, color_code=? WHERE id=?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, priorityName);
            ps.setString(2, colorCode);
            ps.setInt(3, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PriorityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM priorities WHERE id=?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PriorityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Priority> getList() {
        String sql = "SELECT id, priority_name, color_code FROM priorities ORDER BY id DESC";
        List<Priority> list = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Priority p = new Priority(
                        rs.getInt("id"),
                        rs.getString("priority_name"),
                        InputValidator.safeColor(rs.getString("color_code"))
                );
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PriorityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
