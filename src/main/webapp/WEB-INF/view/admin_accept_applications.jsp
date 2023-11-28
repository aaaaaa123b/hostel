<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 26.11.2023
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
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

<h2>Все бронирования</h2>

<c:if test="${not empty requestScope.accept_applications}">
    <table border="1">
        <thead>
        <tr>
            <th>Reservation ID</th>
            <th>Number of Seats</th>
            <th>Reservation Type</th>
            <th>User id</th>
            <th>Hostel id</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reservation" items="${requestScope.accept_applications}">
            <tr>
                <td>${reservation.id}</td>
                <td>${reservation.numberOfSeats}</td>
                <td>${reservation.type}</td>
                <td>${reservation.user_id}</td>
                <td>${reservation.hostel_id}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty requestScope.accept_applications}">
    <p> Нет бронирований</p>
</c:if>

<a href="<c:url value='/dashboard' />" class="button">Назад</a>

</body>
</html>
