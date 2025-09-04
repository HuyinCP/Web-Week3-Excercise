<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quên mật khẩu</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background: #f0f2f5; }
        .container { max-width: 400px; margin: 80px auto; padding: 20px; background: white;
            border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        label { display: block; margin: 10px 0 5px; font-weight: bold; }
        input { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 4px; }
        button { width: 100%; padding: 12px; background: #007BFF; color: white;
            border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        button:hover { background: #0056b3; }
        .message { text-align: center; margin-bottom: 15px; font-size: 14px; }
        .error { color: #d9534f; }
        .success { color: #28a745; }
        .links { text-align: center; margin-top: 15px; font-size: 14px; }
        .links a { color: #007BFF; text-decoration: none; margin: 0 8px; }
        .links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="container">
    <h2>🔑 Quên mật khẩu</h2>

    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="message success">${message}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/forgot-password">
        <label for="email">📧 Email:</label>
        <input type="email" id="email" name="email" placeholder="Nhập email đã đăng ký" required>

        <label for="newPassword">🔒 Mật khẩu mới:</label>
        <input type="password" id="newPassword" name="newPassword" placeholder="Nhập mật khẩu mới" required>

        <label for="confirmPassword">✅ Xác nhận mật khẩu:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu mới" required>

        <button type="submit">Đổi mật khẩu</button>
    </form>

    <div class="links">
        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a> |
        <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
    </div>
</div>
</body>
</html>
