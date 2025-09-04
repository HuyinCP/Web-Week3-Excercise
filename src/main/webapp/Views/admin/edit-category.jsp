<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sửa danh mục</title>
</head>
<body>
<h2>Sửa danh mục</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/category/edit">
    <input type="hidden" name="id" value="${category.id}">
    <label>Tên danh mục: <input type="text" name="name" value="${category.name}" required></label><br/>
    <label>Icon (text/url): <input type="text" name="icon" value="${category.icon}"></label><br/>
    <button type="submit">Cập nhật</button>
    <a href="${pageContext.request.contextPath}/admin/category/list">Hủy</a>
</form>
</body>
</html>
