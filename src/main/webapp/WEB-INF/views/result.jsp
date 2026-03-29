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
    <div class="container">
        <div class="user-stats">
            <p class="stat">Игрок: <span>${playerName}</span></p>

            <p class="stat">Сыграно игр: <span>${gamesPlayed}</span></p>

            <p class="stat">Поражений: <span>${gamesLost}</span></p>
        </div>

        <div class="result-wrapper">
            <p class="result">${resultMessage}</p>

            <form action="${pageContext.request.contextPath}/game" method="get">
                <input type="hidden" name="restart" value="true" />
                <button type="submit" class="custom-button">Играть заново</button>
            </form>
        </div>
    </div>
</body>
</html>