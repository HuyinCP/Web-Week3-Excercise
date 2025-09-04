<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .container { max-width: 400px; margin: auto; }
        h2 { text-align: center; }
        form { border: 1px solid #ccc; padding: 20px; border-radius: 6px; background: #f9f9f9; }
        .input-group { display: flex; margin-bottom: 15px; }
        .input-group-addon { padding: 10px; background: #eee; border: 1px solid #ccc; border-right: none; }
        .form-control { flex: 1; padding: 10px; border: 1px solid #ccc; }
        button { width: 100%; padding: 10px; background: #007BFF; color: #fff; border: none; cursor: pointer; }
        button:hover { background: #0056b3; }
        .alert { padding: 10px; margin-bottom: 15px; text-align: center; }
        .alert-danger { background: #f8d7da; color: #842029; }
    </style>
</head>
<body>
<div class="container">
    <h2>Đăng nhập</h2>

    <c:if test="${not empty alert}">
        <div class="alert alert-danger">${alert}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="input-group">
            <span class="input-group-addon"><i class="fa fa-user"></i></span>
            <input type="text" placeholder="Tài khoản" name="username" class="form-control" required>
        </div>

        <div class="input-group">
            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
            <input type="password" placeholder="Mật khẩu" name="password" class="form-control" required>
        </div>

        <button type="submit">Đăng nhập</button>
    </form>
</div>
</body>
</html>
