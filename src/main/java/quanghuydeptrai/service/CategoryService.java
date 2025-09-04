package org.example.trangdangnhap.service;

import org.example.trangdangnhap.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    // ===== CRUD =====
    void addCategory(Category category);
    void updateCategory(Category category);
    void removeCategory(int id, Long userId);

    // ===== Lấy dữ liệu =====
    Optional<Category> findById(int id, Long userId);
    Optional<Category> findByName(String name, Long userId);
    List<Category> findAllByUser(Long userId);

    // ===== Tìm kiếm =====
    List<Category> search(Long userId, String keyword);
}
