<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="web.models.PointModel" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Лабораторная работа №2 | Главная страница</title>
    <link rel="icon" type="image/jpg" href="media/favicon.ico">
    <link rel="stylesheet" type="text/css" href="style/index.css">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
</head>
<body>
<header>
    Киселёв Михаил Васильевич 489408
</header>
<main class = "main">
    <div class = "container">
        <div class="main__block">
            <div id = "error"></div>
            <canvas id="graph" width="300" height="300"></canvas>
        </div>
        <form id="data_form" action="${pageContext.request.contextPath}/controller" method="post">
            <div class = "wrapper_form">
                <div class="wrapper_input">
                    <span>Выберете X:</span>
                    <div class = "buttons_wrapper">
                        <button name = "x-value" type="button" class = "button_x" value="-2">-2</button>
                        <button name = "x-value" type="button" class = "button_x" value="-1.5">-1.5</button>
                        <button name = "x-value" type="button" class = "button_x" value="-1">-1</button>
                        <button name = "x-value" type="button" class = "button_x" value="-0.5">-0.5</button>
                        <button name = "x-value" type="button" class = "button_x" value="0">0</button>
                        <button name = "x-value" type="button" class = "button_x" value="0.5">0.5</button>
                        <button name = "x-value" type="button" class = "button_x" value="1.0">1.0</button>
                        <button name = "x-value" type="button" class = "button_x" value="1.5">1.5</button>
                        <button name = "x-value" type="button" class = "button_x" value="2">2</button>
                    </div>
                </div>
                <div class="wrapper_input">
                    <span>Выберете Y:</span>
                    <input name="y-value" id="y-input" type="text" placeholder="значение от -3 до 3"  maxlength="2" required>
    <%--                pattern="^-[0-3][0-3]"--%>
                </div>
                <div class="wrapper_input">
                    <select id = "choice" name="choice">
                        <option selected disabled>Выберете R:</option>
                        <option value="1">1</option>
                        <option value="1.5">1.5</option>
                        <option value="2">2</option>
                        <option value="2.5">2.5</option>
                        <option value="3">3</option>
                    </select>
                </div>
            <button id = "submit_button" type="submit">Submit</button>
        </div>
        </form>
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
    </div>
</main>
<footer>
</footer>
</body>
<script src="main.js"></script>
</html>