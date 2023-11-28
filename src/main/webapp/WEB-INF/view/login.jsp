<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 19.11.2023
  Time: 3:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>

</head>
<body>

<div class="form">

    <h1>Вход в систему</h1><br>
    <form method="post" action="${pageContext.request.contextPath}/login">

        <label>
            <input type="text" required placeholder="login" name="login">
        </label><br>
        <label>
            <input type="password" required placeholder="password" name="password">
        </label><br><br>
        <input class="button" type="submit" value="Войти">

    </form>
</div>
</body>
</html>
