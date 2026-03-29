package util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static util.Constants.*;

public final class GameUtil {
    private GameUtil() {}

    public static void initGame(HttpSession session, String playerName, Integer startQuestionId) {
        session.setAttribute(ATTR_PLAYER_NAME, playerName);
        session.setAttribute(ATTR_CURRENT_QUESTION_ID, startQuestionId);
        session.setAttribute(ATTR_GAMES_PLAYED, 0);
        session.setAttribute(ATTR_GAMES_LOST, 0);
    }

    public static String getPlayerName(HttpSession session) {
        return (String) session.getAttribute(ATTR_PLAYER_NAME);
    }

    public static Integer getCurrentQuestionId(HttpSession session) {
        return (Integer) session.getAttribute(ATTR_CURRENT_QUESTION_ID);
    }

    public static void setCurrentQuestionId(HttpSession session, Integer id) {
        session.setAttribute(ATTR_CURRENT_QUESTION_ID, id);
    }

    public static void incrementGamesPlayed(HttpSession session) {
        Integer played = (Integer) session.getAttribute(ATTR_GAMES_PLAYED);
        session.setAttribute(ATTR_GAMES_PLAYED, (played == null ? 0 : played) + 1);
    }

    public static void incrementGamesLost(HttpSession session) {
        Integer lost = (Integer) session.getAttribute(ATTR_GAMES_LOST);
        session.setAttribute(ATTR_GAMES_LOST, (lost == null ? 0 : lost) + 1);
    }

    public static Integer getGamesPlayed(HttpSession session) {
        Integer value = (Integer) session.getAttribute(ATTR_GAMES_PLAYED);
        return value == null ? 0 : value;
    }

    public static Integer getGamesLost(HttpSession session) {
        Integer value = (Integer) session.getAttribute(ATTR_GAMES_LOST);
        return value == null ? 0 : value;
    }
}
