<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 19.11.2023
  Time: 3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ADMIN</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 20px;
        }

        h1 {
            color: #333;
        }

        .button {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            outline: none;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            box-shadow: 0 3px #007bff;
        }

        .button:hover {
            background-color: #0056b3;
        }

        .content {
            margin-top: 30px;
        }

        table {
            border-collapse: collapse;
            width: 80%;
            margin: auto;
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

<form action="${pageContext.request.contextPath}/adminList" method="get">
    <button type="submit" class="button">Заявки</button>
</form>

<form action="${pageContext.request.contextPath}/admin" method="get">
    <button type="submit" class="button">Непринятые заявки</button>
</form>

<a href="${pageContext.request.contextPath}/logout" class="button">Выход</a>

</body>
</html>
