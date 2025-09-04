package org.example.trangdangnhap.dao.impl;

import org.example.trangdangnhap.dao.UserDAO;
import org.example.trangdangnhap.model.User;
import org.example.trangdangnhap.util.DBConnection;

import java.sql.*;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private static final String SQL_INSERT =
            "INSERT INTO [User] (username, password, email) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_LOGIN =
            "SELECT TOP 1 * FROM [User] WHERE username = ? AND password = ?";
    private static final String SQL_CHECK_EXISTS =
            "SELECT 1 FROM [User] WHERE username = ?";
    private static final String SQL_SELECT_BY_EMAIL =
            "SELECT TOP 1 * FROM [User] WHERE email = ?";
    private static final String SQL_SELECT_BY_ID =
            "SELECT TOP 1 * FROM [User] WHERE id = ?";
    private static final String SQL_UPDATE_PASSWORD =
            "UPDATE [User] SET password = ? WHERE id = ?";
    private static final String SQL_CHECK_EMAIL_EXISTS =
            "SELECT 1 FROM [User] WHERE email = ?";

    @Override
    public boolean registerUser(User user) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        user.setId(keys.getLong(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error registering user", e);
        }
    }

    @Override
    public User loginUser(String username, String password) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_SELECT_LOGIN)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error during login", e);
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_CHECK_EXISTS)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking username existence", e);
        }
    }

    @Override
    public User getUserById(Long id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id", e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email", e);
        }
    }

    @Override
    public boolean updatePassword(Long userId, String newPassword) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            ps.setString(1, newPassword);
            ps.setLong(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating password", e);
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_CHECK_EMAIL_EXISTS)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking email existence", e);
        }
    }

    // ===== helper method =====
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));

        // cột roleId có thể null
        int role = rs.getInt("roleId");
        if (!rs.wasNull()) {
            user.setRoleId(role);
        }
        return user;
    }
}
