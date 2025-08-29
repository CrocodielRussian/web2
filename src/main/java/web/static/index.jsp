<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Лабораторная работа №2</title>
    <link rel="icon" type="image/jpg" href="media/favicon.ico">
    <link rel="stylesheet" type="text/css" href="style/index.css">
</head>
<body>
<header>
    Киселёв Михаил Васильевич 489408
</header>
<main class = "main">
    <div class = "container">
        <div class="main__block">
            <img class="graph" src="image/areas.png"/>
        </div>
        <form method="get" id="data_form">
            <div class="wrapper_input">
                <span><span>Выберете X:</span><input name="x-value" id="x-input" type="text" placeholder="значение от -3 до 3" maxlength="2" required></span>
            </div>
            <div class="wrapper_input">
                <span><span>Выберете Y:</span><input name="y-value" id="y-input" type="text" placeholder="значение от -5 до 5" maxlength="2" required></span>
                <!-- <span></span> -->

            </div>
            <div class="wrapper_input">
                <span>Выберете R:</span>
                <p><input name="r-value" type="radio" value="1" required>1.0</p>
                <p><input name="r-value" type="radio" value="1.5">1.5</p>
                <p><input name="r-value" type="radio" value="2">2</p>
                <p><input name="r-value" type="radio" value="2.5">2.5</p>
                <p><input name="r-value" type="radio" value="3">3</p>
            </div>
            <button type="submit">Submit</button>
        </form>
        <div class="result-container">
            <div class="result-title">Результат</div>
            <div style="border-top: 1px solid #000000;">Время</div>
            <div id="x" class="result-rows">X</div>
            <div id="y" class="result-rows">Y</div>
            <div id="r" class="result-rows">R</div>
            <div id="aim" class="result-rows">Попал?</div>
            <div id="time" class="result-rows">Время выполнения</div>
        </div>
        <div id="result"></div>
        <div id="error"></div>
    </div>
    </div>
</main>
<footer>
    @Все права защищены 2025
</footer>
</body>
<script src="main.js"></script>
</html>