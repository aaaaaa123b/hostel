<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 27.11.2023
  Time: 0:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>USER</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        h1 {
            color: #333;
            text-align: center;
        }

        label {
            max-width: 250px;
            display: block;
            margin-bottom: 10px;
        }

        select, input {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        a {
            display: block;
            margin-top: 20px;
            color: #3498db;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        form {
            max-width: 250px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
    </style>
</head>

<body>

<form action="<c:url value='/hostelReservation' />" method="get">

    <label>Тип бронирования:</label>

    <label>
        <select name="reservationType">
            <option value="бронирование">Бронирование</option>
            <option value="полная оплата">Полная оплата</option>
        </select>
    </label>
    <c:set var="apartment" value="${requestScope.apartment}"/>

    <div class="action-buttons">
        <input type="hidden" name="apartment_id" value="${apartment.id}">
        <input type="hidden" name="apartment_number" value="${apartment.room_number}">
        <input type="hidden" name="type" value="${apartment.type}">
        <input type="submit" name="action" value="Забронировать" class="accept">
    </div>

    <a href="<c:url value='/apartments' />" class="button">Выход</a>
</form>
</body>
</html>
