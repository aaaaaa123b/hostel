<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            width: 100%;
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


        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #4caf50;
            color: white;
        }

        .action-buttons {
            width: 150px;
        }

        .accept {
            background-color: #4caf50;
            color: white;
            cursor: pointer;
            width: 100%;
        }

        .accept:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<h1>Список отелей</h1>

<c:if test="${not empty requestScope.hostelList}">
    <table border="1">
        <thead>
        <tr>
            <th>Hostel name</th>
            <th>Location</th>
            <th>Capacity</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.hostelList}" var="hostel">

            <tr>
                <td>${hostel.hostel_name}</td>
                <td>${hostel.location}</td>
                <td>${hostel.capacity}</td>
                <td class="action-buttons">
                    <form action="${pageContext.request.contextPath}/reservationServlet" method="get">
                        <input type="hidden" name="hostelId" value="${hostel.id}">
                        <input type="submit" name="action" value="Забронировать" class="accept">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty requestScope.hostelList}">
    <p> Нет отелей</p>
</c:if>

<a href="<c:url value='/logout' />" class="button">Выход</a>
</body>
</html>

