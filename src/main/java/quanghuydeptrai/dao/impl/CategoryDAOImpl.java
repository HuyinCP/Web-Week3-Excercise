package org.example.trangdangnhap.dao.impl;

import org.example.trangdangnhap.dao.CategoryDAO;
import org.example.trangdangnhap.model.Category;
import org.example.trangdangnhap.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private static final String SQL_INSERT =
            "INSERT INTO Category(cate_name, icons, user_id) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE Category SET cate_name = ?, icons = ? WHERE cate_id = ? AND user_id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM Category WHERE cate_id = ? AND user_id = ?";
    private static final String SQL_GET_BY_ID =
            "SELECT * FROM Category WHERE cate_id = ? AND user_id = ?";
    private static final String SQL_GET_BY_NAME =
            "SELECT * FROM Category WHERE cate_name = ? AND user_id = ?";
    private static final String SQL_GET_ALL =
            "SELECT * FROM Category WHERE user_id = ? ORDER BY cate_id DESC";
    private static final String SQL_SEARCH =
            "SELECT * FROM Category WHERE user_id = ? AND cate_name LIKE ? ORDER BY cate_id DESC";

    @Override
    public void insert(Category category) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getIcon());
            ps.setLong(3, category.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting category", e);
        }
    }

    @Override
    public void edit(Category category) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getIcon());
            ps.setInt(3, category.getId());
            ps.setLong(4, category.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating category", e);
        }
    }

    @Override
    public void delete(int id, Long userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting category", e);
        }
    }

    @Override
    public Category get(int id, Long userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_GET_BY_ID)) {
            ps.setInt(1, id);
            ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding category by id", e);
        }
        return null;
    }

    @Override
    public Category get(String name, Long userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_GET_BY_NAME)) {
            ps.setString(1, name);
            ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding category by name", e);
        }
        return null;
    }

    @Override
    public List<Category> getAllByUserId(Long userId) {
        List<Category> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all categories by userId", e);
        }
        return list;
    }

    @Override
    public List<Category> search(Long userId, String keyword) {
        List<Category> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SEARCH)) {
            ps.setLong(1, userId);
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching categories", e);
        }
        return list;
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("cate_id"));
        category.setName(rs.getString("cate_name"));
        category.setIcon(rs.getString("icons"));
        long uid = rs.getLong("user_id");
        if (!rs.wasNull()) category.setUserId(uid);
        return category;
    }
}
