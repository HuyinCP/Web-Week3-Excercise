package org.example.trangdangnhap.service.impl;

import org.example.trangdangnhap.dao.CategoryDAO;
import org.example.trangdangnhap.dao.impl.CategoryDAOImpl;
import org.example.trangdangnhap.model.Category;
import org.example.trangdangnhap.service.CategoryService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
    }

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public void insert(Category category) {
        if (category == null) return;
        categoryDAO.insert(category);
    }

    @Override
    public void edit(Category category) {
        if (category == null || category.getId() == null || category.getUserId() == null) return;
        categoryDAO.edit(category);
    }

    @Override
    public void delete(int id, Long userId) {
        if (userId == null) return;
        categoryDAO.delete(id, userId);
    }

    @Override
    public Category get(int id, Long userId) {
        if (userId == null) return null;
        return categoryDAO.get(id, userId);
    }

    @Override
    public Category get(String name, Long userId) {
        if (userId == null || name == null || name.isBlank()) return null;
        return categoryDAO.get(name, userId);
    }

    @Override
    public List<Category> getAllByUserId(Long userId) {
        if (userId == null) return Collections.emptyList();
        return categoryDAO.getAllByUserId(userId);
    }

    @Override
    public List<Category> search(Long userId, String keyword) {
        if (userId == null) return Collections.emptyList();
        return categoryDAO.search(userId, keyword == null ? "" : keyword);
    }
}
