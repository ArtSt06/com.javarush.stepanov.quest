<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Text quest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container starting-menu">
        <div class="greeting">
            <h1>Добро пожаловать в текстовый квест!</h1>

            <p class="greeting-text">
                <span>Ваша предыстория:</span>
                ${intro}
            </p>
        </div>

        <form action="${pageContext.request.contextPath}/answer"
              method="post"
              class="user-intro-form"
        >
            <div class="form-group">
                <label for="playerName">Введите ваше имя:</label>

                <input type="text" id="playerName" name="playerName" required />
            </div>

            <button type="submit" class="custom-button">Начать игру</button>
        </form>
    </div>
</body>
</html>