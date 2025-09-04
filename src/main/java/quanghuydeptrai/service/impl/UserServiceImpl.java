package org.example.trangdangnhap.service.impl;

import org.example.trangdangnhap.dao.UserDAO;
import org.example.trangdangnhap.dao.impl.UserDAOImpl;
import org.example.trangdangnhap.model.User;
import org.example.trangdangnhap.service.UserService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // ========== ĐĂNG KÝ ==========
    @Override
    public boolean register(User user) {
        if (!isValidUserForRegister(user)) return false;
        if (userDAO.isUsernameExists(user.getUsername())) return false;

        user.setPassword(hashPassword(user.getPassword()));
        return userDAO.registerUser(user);
    }

    // ========== LOGIN ==========
    @Override
    public Optional<User> login(String username, String password) {
        if (isNullOrEmpty(username) || isNullOrEmpty(password)) {
            return Optional.empty();
        }
        String hashed = hashPassword(password);
        return Optional.ofNullable(userDAO.loginUser(username, hashed));
    }

    // ========== QUÊN MẬT KHẨU ==========
    @Override
    public boolean forgotPassword(String email, String newPassword) {
        if (!isValidEmail(email) || !isStrongPassword(newPassword)) return false;

        User user = userDAO.getUserByEmail(email);
        if (user == null) return false;

        return userDAO.updatePassword(user.getId(), hashPassword(newPassword));
    }

    // ========== ĐỔI MẬT KHẨU ==========
    @Override
    public boolean changePassword(Long userId, String currentPassword, String newPassword) {
        if (userId == null || !isStrongPassword(newPassword)) return false;

        User user = userDAO.getUserById(userId);
        if (user == null) return false;

        String currentHash = hashPassword(currentPassword);
        if (!currentHash.equals(user.getPassword())) return false;

        return userDAO.updatePassword(userId, hashPassword(newPassword));
    }

    // ========== CHECK ==========
    @Override
    public boolean isEmailExists(String email) {
        return !isNullOrEmpty(email) && userDAO.isEmailExists(email);
    }

    // ========== HELPER ==========
    private boolean isValidUserForRegister(User user) {
        return user != null
                && !isNullOrEmpty(user.getUsername())
                && !isNullOrEmpty(user.getPassword())
                && !isNullOrEmpty(user.getEmail())
                && isValidEmail(user.getEmail())
                && isStrongPassword(user.getPassword());
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    private boolean isStrongPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    private String hashPassword(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not supported", e);
        }
    }
}
