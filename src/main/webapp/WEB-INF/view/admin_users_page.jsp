<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 30.11.2023
  Time: 2:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Reservations</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h2 {
            color: #333;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

    </style>
</head>
<body>

<h2>Все пользователи</h2>

<c:if test="${not empty requestScope.users}">
    <table border="1">
        <thead>
        <tr>
            <th>User ID</th>
            <th>Login</th>
            <th>Password</th>
            <th>Block</th>
            <th>Sale</th>
            <th>Order_number</th>
            <th>Edit</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td>${user.user_id}</td>
                <td>${user.login}</td>
                <td>${user.password}</td>
                <td>${user.block}</td>
                <td>${user.sale}</td>
                <td>${user.order_number}</td>
                <td>
                    <form action="<c:url value='/users' />" method="post">
                        <input type="hidden" name="userId" value="${user.user_id}" />

                        <label for="block">Block:</label>
                        <input type="checkbox" id="block" name="block" ${user.block ? 'checked' : ''} />

                        <label for="sale">Sale:</label>
                        <input type="text" id="sale" name="sale" value="${user.sale}" />

                        <input type="submit" value="Edit" />
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty requestScope.users}">
    <p> Нет пользователей</p>
</c:if>

<a href="<c:url value='/dashboard' />" class="button">Назад</a>

</body>
</html>
