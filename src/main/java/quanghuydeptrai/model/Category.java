package org.example.trangdangnhap.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // cate_id

    @Column(name = "cate_name", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "user_id", nullable = false)
    private Long userId; // khóa ngoại đến User.id

    // Quan hệ nhiều Category thuộc về 1 User
    // Nếu bạn dùng JPA đầy đủ có thể thay bằng:
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id", insertable = false, updatable = false)
    // private User user;

    // ===== Constructors =====
    public Category() {}

    public Category(Integer id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public Category(Integer id, String name, String icon, Long userId) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.userId = userId;
    }

    // ===== Getters / Setters =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    // ===== tiện ích =====
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userId);
    }
}
