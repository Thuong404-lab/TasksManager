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
import com.taskmanager.model.User;
import com.taskmanager.util.PasswordHasher;

public class UserDAO extends DatabaseConnectionProvider {

    public User login(String username, String password) {
        String sql = "SELECT id, user_account, user_password, user_name, user_email, role "
                + "FROM users WHERE user_account=?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && PasswordHasher.matches(password, rs.getString("user_password"))) {
                    int id = rs.getInt("id");
                    String storedHash = rs.getString("user_password");
                    if (PasswordHasher.needsUpgrade(storedHash)) {
                        // Nâng cấp trong suốt: người dùng cũ không phải tự đặt lại mật khẩu.
                        upgradePassword(connection, id, password);
                    }
                    return new User(id, rs.getString("user_account"),
                            rs.getString("user_name"), rs.getString("user_email"), rs.getString("role")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void upgradePassword(Connection connection, int id, String rawPassword) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE users SET user_password=? WHERE id=?")) {
            ps.setString(1, PasswordHasher.hash(rawPassword));
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public List<User> getAll() {
        return getList();
    }

    public int countUsers() {
        String sql = "SELECT COUNT(*) AS total FROM users";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int countByRole(String role) {
        String sql = "SELECT COUNT(*) AS total FROM users WHERE LOWER(role)=LOWER(?)";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public User getById(int id) {
        String sql = "SELECT id, user_account, user_name, user_email, role FROM users WHERE id=?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("user_account"),
                            rs.getString("user_name"),
                            rs.getString("user_email"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean insert(String userAccount, String rawPassword, String userName, String userEmail, String role) {
        String sql = "INSERT INTO users (user_account, user_password, user_name, user_email, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userAccount);
            ps.setString(2, PasswordHasher.hash(rawPassword));
            ps.setString(3, userName);
            ps.setString(4, userEmail);
            ps.setString(5, role);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update(int id, String userAccount, String rawPassword, String userName, String userEmail, String role) {
        String sqlWithPassword = "UPDATE users SET user_account=?, user_password=?, user_name=?, user_email=?, role=? WHERE id=?";
        String sqlNoPassword = "UPDATE users SET user_account=?, user_name=?, user_email=?, role=? WHERE id=?";

        boolean hasPassword = rawPassword != null && !rawPassword.isBlank();

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(hasPassword ? sqlWithPassword : sqlNoPassword)) {
            ps.setString(1, userAccount);
            if (hasPassword) {
                ps.setString(2, PasswordHasher.hash(rawPassword));
                ps.setString(3, userName);
                ps.setString(4, userEmail);
                ps.setString(5, role);
                ps.setInt(6, id);
            } else {
                ps.setString(2, userName);
                ps.setString(3, userEmail);
                ps.setString(4, role);
                ps.setInt(5, id);
            }

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<User> getList() {
        String sql = "SELECT id, user_account, user_name, user_email, role FROM users ORDER BY id DESC";
        List<User> list = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                User u = new User(
                        rs.getInt("id"),
                        rs.getString("user_account"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("role")
                );
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
