<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm danh mục</title>
</head>
<body>
<h2>Thêm danh mục</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/category/add">
    <label>Tên danh mục: <input type="text" name="name" required></label><br/>
    <label>Icon (text/url): <input type="text" name="icon"></label><br/>
    <button type="submit">Lưu</button>
    <a href="${pageContext.request.contextPath}/admin/category/list">Hủy</a>
</form>
</body>
</html>
