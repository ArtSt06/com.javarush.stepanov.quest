<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
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

        <div class="question-wrapper">
            <p class="question">${questionText}</p>

            <form action="${pageContext.request.contextPath}/game" method="post" class="answer-options">
                <c:forEach items="${answers}" var="answer" varStatus="status">
                    <button type="submit" name="answer" value="${status.index}" class="custom-button">${answer.text}</button>
                </c:forEach>
            </form>
        </div>
    </div>
</body>
</html>