package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Users;

public class UsersDAO extends db.DBContext {

    private String hashMd5(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mess = md.digest(raw.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : mess) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public Users login(String username, String password) {
        String sql = "SELECT id, user_account, user_password, user_name, user_email, role "
                + "FROM users WHERE user_account=? AND user_password=?";

        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashMd5(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Users(rs.getInt("id"), rs.getString("user_account"), rs.getString("user_password"),
                            rs.getString("user_name"), rs.getString("user_email"), rs.getString("role")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Users> getAll() {
        return getList();
    }

    public int countUsers() {
        String sql = "SELECT COUNT(*) AS total FROM users";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int countByRole(String role) {
        String sql = "SELECT COUNT(*) AS total FROM users WHERE LOWER(role)=LOWER(?)";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Users getById(int id) {
        String sql = "SELECT id, user_account, user_password, user_name, user_email, role FROM users WHERE id=?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Users(
                            rs.getInt("id"),
                            rs.getString("user_account"),
                            rs.getString("user_password"),
                            rs.getString("user_name"),
                            rs.getString("user_email"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean insert(String userAccount, String rawPassword, String userName, String userEmail, String role) {
        String sql = "INSERT INTO users (user_account, user_password, user_name, user_email, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setString(1, userAccount);
            ps.setString(2, hashMd5(rawPassword));
            ps.setString(3, userName);
            ps.setString(4, userEmail);
            ps.setString(5, role);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update(int id, String userAccount, String rawPassword, String userName, String userEmail, String role) {
        String sqlWithPassword = "UPDATE users SET user_account=?, user_password=?, user_name=?, user_email=?, role=? WHERE id=?";
        String sqlNoPassword = "UPDATE users SET user_account=?, user_name=?, user_email=?, role=? WHERE id=?";

        boolean hasPassword = rawPassword != null && !rawPassword.isBlank();

        try (PreparedStatement ps = this.getConnection().prepareStatement(hasPassword ? sqlWithPassword : sqlNoPassword)) {
            ps.setString(1, userAccount);
            if (hasPassword) {
                ps.setString(2, hashMd5(rawPassword));
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
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Users> getList() {
        String sql = "SELECT * FROM users ORDER BY id DESC";
        List<Users> list = new ArrayList<>();

        try (PreparedStatement ps = this.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                Users u = new Users(
                        rs.getInt("id"),
                        rs.getString("user_account"),
                        rs.getString("user_password"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("role")
                );
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
