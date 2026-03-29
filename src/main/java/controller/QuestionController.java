package controller;

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

@WebServlet("/game")
public class QuestionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (req.getParameter(ATTR_RESTART) != null) {
            GameUtil.restartGame(session, START_QUESTION_ID);
            showCurrentQuestion(req, resp, session);
            return;
        }

        String playerName = GameUtil.getPlayerName(session);
        Integer currentId = GameUtil.getCurrentQuestionId(session);

        if (playerName == null || currentId == null) {
            req.setAttribute(ATTR_INTRO, QuestData.getIntro());
            req.getRequestDispatcher("/WEB-INF/start.jsp").forward(req, resp);
            return;
        }

        showCurrentQuestion(req, resp, session);
    }

    private void showCurrentQuestion(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        int currentId = GameUtil.getCurrentQuestionId(session);
        Question question = QuestData.getQuestionById(currentId);
        if (question == null) {
            req.setAttribute(ATTR_INTRO, QuestData.getIntro());
            req.getRequestDispatcher("/WEB-INF/start.jsp").forward(req, resp);
            return;
        }

        req.setAttribute(ATTR_QUESTION_TEXT, question.getText());
        req.setAttribute(ATTR_ANSWERS, question.getAnswers());
        req.setAttribute(ATTR_PLAYER_NAME, GameUtil.getPlayerName(session));
        req.setAttribute(ATTR_GAMES_PLAYED, GameUtil.getGamesPlayed(session));
        req.setAttribute(ATTR_GAMES_LOST, GameUtil.getGamesLost(session));
        req.getRequestDispatcher("/WEB-INF/question.jsp").forward(req, resp);
    }
}