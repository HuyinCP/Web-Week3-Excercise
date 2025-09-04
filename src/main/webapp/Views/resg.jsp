<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký tài khoản</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .container { max-width: 400px; margin: auto; }
        h2 { text-align: center; }
        form { border: 1px solid #ccc; padding: 20px; border-radius: 6px; background: #f9f9f9; }
        label { display: block; margin: 10px 0 5px; }
        input { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }
        button { width: 100%; padding: 10px; background: #28a745; color: white; border: none; cursor: pointer; }
        button:hover { background: #218838; }
        .message { text-align: center; margin: 10px 0; }
        .error { color: red; }
        .success { color: green; }
        .links { text-align: center; margin-top: 10px; }
        .links a { text-decoration: none; color: #007BFF; }
    </style>
</head>
<body>
<div class="container">
    <h2>Đăng ký tài khoản</h2>

    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="message success">${message}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/register">
        <label for="username">Tên đăng nhập:</label>
        <input type="text" id="username" name="username" placeholder="Nhập username" required>

        <label for="password">Mật khẩu:</label>
        <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" placeholder="Nhập email" required>

        <button type="submit">Đăng ký</button>
    </form>

    <div class="links">
        <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>
    </div>
</div>
</body>
</html>
