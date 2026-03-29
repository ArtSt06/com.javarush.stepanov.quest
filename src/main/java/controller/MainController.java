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
import static util.Constants.ATTR_GAMES_LOST;
import static util.Constants.ATTR_GAMES_PLAYED;
import static util.Constants.ATTR_PLAYER_NAME;

@WebServlet("/game")
public class MainController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (req.getParameter(ATTR_RESTART) != null) {
            GameUtil.setCurrentQuestionId(session, START_QUESTION_ID);
            showCurrentQuestion(req, resp, session);
            return;
        }

        String playerName = GameUtil.getPlayerName(session);
        Integer currentId = GameUtil.getCurrentQuestionId(session);

        if (playerName == null || currentId == null) {
            restartGame(req, resp);
        }

        showCurrentQuestion(req, resp, session);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String playerName = req.getParameter(ATTR_PLAYER_NAME);
        if (playerName != null && !playerName.trim().isEmpty()) {
            GameUtil.initGame(session, playerName.trim(), START_QUESTION_ID);
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
        Integer answerIndex;
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
            setStatisticalAttributes(req, session);
            req.setAttribute(ATTR_RESULT_MESSAGE, answer.getResultText());
            req.getRequestDispatcher("/WEB-INF/views/result.jsp").forward(req, resp);
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

    private void showCurrentQuestion(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
        Integer currentId = GameUtil.getCurrentQuestionId(session);
        Question question = QuestData.getQuestionById(currentId);

        if (question == null) {
            restartGame(req, resp);
        }

        setStatisticalAttributes(req, session);
        req.setAttribute(ATTR_QUESTION_TEXT, question.getText());
        req.setAttribute(ATTR_ANSWERS, question.getAnswers());
        req.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(req, resp);
    }

    private void restartGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ATTR_INTRO, QuestData.getIntro());
        req.getRequestDispatcher("/WEB-INF/views/start.jsp").forward(req, resp);
    }

    private void setStatisticalAttributes(HttpServletRequest req, HttpSession session) {
        req.setAttribute(ATTR_PLAYER_NAME, GameUtil.getPlayerName(session));
        req.setAttribute(ATTR_GAMES_PLAYED, GameUtil.getGamesPlayed(session));
        req.setAttribute(ATTR_GAMES_LOST, GameUtil.getGamesLost(session));
    }
}
