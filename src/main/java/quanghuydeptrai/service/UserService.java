package org.example.trangdangnhap.service;

import org.example.trangdangnhap.model.User;

import java.util.Optional;

public interface UserService {

    // Đăng ký & đăng nhập
    boolean register(User user);
    Optional<User> login(String username, String password);

    // Quản lý mật khẩu
    boolean resetPassword(String email, String newPassword);
    boolean updatePassword(Long userId, String currentPassword, String newPassword);

    // Kiểm tra thông tin
    boolean emailExists(String email);
}
