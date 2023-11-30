<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 21.11.2023
  Time: 17:57
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

        .action-buttons {
            text-align: center;
        }

        .accept, .reject {
            padding: 5px 10px;
            margin: 5px;
        }
    </style>
</head>
<body>

<h2>Все заявки</h2>

<c:if test="${not empty requestScope.reservations}">
    <table border="1">
        <thead>
        <tr>
            <th>Reservation ID</th>
            <th>Room number</th>
            <th>Reservation Type</th>
            <th>User id</th>
            <th>Hostel id</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reservation" items="${requestScope.reservations}">
            <tr>
                <td>${reservation.id}</td>
                <td>${reservation.room_number}</td>
                <td>${reservation.type}</td>
                <td>${reservation.user_id}</td>
                <td>${reservation.hostel_id}</td>
                <td class="action-buttons">
                    <form action="${pageContext.request.contextPath}/processReservation" method="get">
                        <input type="hidden" name="reservationId" value="${reservation.id}">
                        <input type="submit" name="action" value="Accept" class="accept">
                        <input type="submit" name="action" value="Reject" class="reject">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty requestScope.reservations}">
    <p> Нет нерассмотренных заявок</p>
</c:if>

<a href="<c:url value='/dashboard' />" class="button">Назад</a>

</body>
</html>
