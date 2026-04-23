package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Priorities;

public class PrioritesDAO extends db.DBContext {

    public Priorities getById(int id) {
        String sql = "SELECT id, priority_name, color_code FROM priorities WHERE id=?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Priorities(
                            rs.getInt("id"),
                            rs.getString("priority_name"),
                            rs.getString("color_code")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrioritesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Priorities> getAll() {
        return getList();
    }

    public boolean insert(String priorityName, String colorCode) {
        String sql = "INSERT INTO priorities (priority_name, color_code) VALUES (?, ?)";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setString(1, priorityName);
            ps.setString(2, colorCode);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PrioritesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean update(int id, String priorityName, String colorCode) {
        String sql = "UPDATE priorities SET priority_name=?, color_code=? WHERE id=?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setString(1, priorityName);
            ps.setString(2, colorCode);
            ps.setInt(3, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PrioritesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM priorities WHERE id=?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PrioritesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Priorities> getList() {
        String sql = "SELECT id, priority_name, color_code FROM priorities ORDER BY id DESC";
        List<Priorities> list = new ArrayList<>();

        try (PreparedStatement ps = this.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Priorities p = new Priorities(
                        rs.getInt("id"),
                        rs.getString("priority_name"),
                        rs.getString("color_code")
                );
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrioritesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
