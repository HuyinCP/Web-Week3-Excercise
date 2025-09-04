package org.example.trangdangnhap.dao;

import org.example.trangdangnhap.model.User;

public interface UserDAO {
    boolean registerUser(User user);

    User loginUser(String username, String password);

    boolean isUsernameExists(String username);
    
    // Thêm các phương thức mới
    User getUserByEmail(String email);
    
    User getUserById(Long id);
    
    boolean updatePassword(Long userId, String newPassword);
    
    boolean isEmailExists(String email);
}


