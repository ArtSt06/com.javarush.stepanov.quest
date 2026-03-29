package controller;

import entity.Answer;
import entity.GameResult;
import entity.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.QuestData;
import util.GameUtil;

import java.io.IOException;

import static util.Constants.*;

@WebServlet("/answer")
public class AnswerController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String playerName = req.getParameter(ATTR_PLAYER_NAME);
        if (playerName != null && !playerName.trim().isEmpty()) {
            GameUtil.startNewGame(session, playerName.trim(), START_QUESTION_ID);
            resp.sendRedirect(req.getContextPath() + "/game");
            return;
        }

        Integer currentId = GameUtil.getCurrentQuestionId(session);
        if (currentId == null) {
            resp.sendRedirect(req.getContextPath() + "/game");
            return;
        }

        Question question = QuestData.getQuestionById(currentId);
        if (question == null) {
            resp.sendRedirect(req.getContextPath() + "/game");
            return;
        }

        String answerParam = req.getParameter(ATTR_ANSWER);
        int answerIndex;
        try {
            answerIndex = Integer.parseInt(answerParam);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/game");
            return;
        }

        if (answerIndex < 0 || answerIndex >= question.getAnswers().size()) {
            resp.sendRedirect(req.getContextPath() + "/game");
            return;
        }

        Answer answer = question.getAnswers().get(answerIndex);

        if (answer.getTerminal()) {
            GameUtil.incrementGamesPlayed(session);
            if (answer.getResult() == GameResult.LOSE) {
                GameUtil.incrementGamesLost(session);
            }
            req.setAttribute(ATTR_PLAYER_NAME, GameUtil.getPlayerName(session));
            req.setAttribute(ATTR_RESULT_MESSAGE, answer.getResultText());
            req.setAttribute(ATTR_GAMES_PLAYED, GameUtil.getGamesPlayed(session));
            req.setAttribute(ATTR_GAMES_LOST, GameUtil.getGamesLost(session));
            req.getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);
            return;
        }

        Integer nextId = answer.getNextQuestionId();
        if (nextId == null) {
            resp.sendRedirect(req.getContextPath() + "/game?restart=true");
            return;
        }

        GameUtil.setCurrentQuestionId(session, nextId);
        resp.sendRedirect(req.getContextPath() + "/game");
    }
}