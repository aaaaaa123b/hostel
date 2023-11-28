<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 28.11.2023
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>

<div class="form">

    <h1>Регистрация</h1><br>
    <form method="post" action="${pageContext.request.contextPath}/">

        <label>
            <input type="text" required placeholder="login" name="login">
        </label><br>
        <label>
            <input type="password" required placeholder="password" name="password">
        </label><br><br>
        <input class="button" type="submit" value="Войти">
    </form>
    <p>Уже зарегистрированы? <a href="${pageContext.request.contextPath}/login">Вход</a></p>
</div>
</body>
</html>

