<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quên mật khẩu</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .container { max-width: 400px; margin: auto; }
        h2 { text-align: center; }
        form { border: 1px solid #ccc; padding: 20px; border-radius: 6px; background: #f9f9f9; }
        label { display: block; margin: 10px 0 5px; }
        input { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }
        button { width: 100%; padding: 10px; background: #007BFF; color: white; border: none; cursor: pointer; }
        button:hover { background: #0056b3; }
        .message { text-align: center; margin: 10px 0; }
        .error { color: red; }
        .success { color: green; }
        .links { text-align: center; margin-top: 10px; }
        .links a { text-decoration: none; color: #007BFF; }
    </style>
</head>
<body>
<div class="container">
    <h2>Quên mật khẩu</h2>

    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="message success">${message}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/forgot-password">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" placeholder="Nhập email đã đăng ký" required>

        <label for="newPassword">Mật khẩu mới:</label>
        <input type="password" id="newPassword" name="newPassword" placeholder="Nhập mật khẩu mới" required>

        <label for="confirmPassword">Xác nhận mật khẩu:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu mới" required>

        <button type="submit">Đổi mật khẩu</button>
    </form>

    <div class="links">
        <a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a> |
        <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
    </div>
</div>
</body>
</html>
