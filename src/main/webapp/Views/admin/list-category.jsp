<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách danh mục</title>
</head>
<body>
<h2>Danh sách danh mục</h2>
<p><a href="${pageContext.request.contextPath}/admin/category/add">Thêm danh mục</a></p>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>STT</th>
        <th>Tên danh mục</th>
        <th>Icon</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${cateList}" var="cate" varStatus="stt">
        <tr>
            <td>${stt.index + 1}</td>
            <td>${cate.name}</td>
            <td>${cate.icon}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/category/edit?id=${cate.id}">Sửa</a>
                |
                <a href="${pageContext.request.contextPath}/admin/category/delete?id=${cate.id}" onclick="return confirm('Xóa danh mục này?');">Xóa</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="${pageContext.request.contextPath}/home">Về trang chủ</a></p>
</body>
</html>
