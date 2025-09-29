<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="web.models.PointModel" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Лабораторная работа №2 | Страница результатов</title>
    <link rel="icon" type="image/jpg" href="media/favicon.ico">
    <link rel="stylesheet" type="text/css" href="style/index.css">
    <link rel="stylesheet" type="text/css" href="style/result.css">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
</head>
<body>
<header>
    Киселёв Михаил Васильевич 489408
</header>
<main class = "main">
    <div class = "container">
        <table id = "result_table">
            <thead>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Попадание</th>
            </tr>
            </thead>
            <tbody>
            <% List<PointModel> data = (List<PointModel>) session.getAttribute("results");
                if (data == null) {
            %>
            <tr>
                <td>X</td>
                <td>Y</td>
                <td>R</td>
                <td>Попал?</td>
            </tr>
            <% } else {
                for (PointModel p : data) { %>
            <tr class = <%= p.isInArea() ? "got" : "missed" %> >
                <td><%= p.getX() %></td>
                <td><%= p.getY() %></td>
                <td><%= p.getR() %></td>
                <td><%= p.isInArea() ? "Попал" : "Мимо" %></td>
            </tr>
            <% }
            } %>
            </tbody>
        </table>
        <div class = "wrapper_link">
            <a id="return" href="index.jsp">Назад</a>
        </div>
    </div>
</main>
<footer>
</footer>
</body>
</html>