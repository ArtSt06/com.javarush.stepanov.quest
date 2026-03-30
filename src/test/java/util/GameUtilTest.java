package util;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameUtilTest {

    @Test
    void shouldInitAndIncrementCounters() {
        HttpSession session = mock(HttpSession.class);

        GameUtil.initGame(session, "Artem", 1);
        when(session.getAttribute(Constants.ATTR_GAMES_PLAYED)).thenReturn(null, 0);
        when(session.getAttribute(Constants.ATTR_GAMES_LOST)).thenReturn(null, 0);

        GameUtil.incrementGamesPlayed(session);
        GameUtil.incrementGamesLost(session);

        verify(session).setAttribute(Constants.ATTR_PLAYER_NAME, "Artem");
        verify(session).setAttribute(Constants.ATTR_CURRENT_QUESTION_ID, 1);
        verify(session).setAttribute(Constants.ATTR_GAMES_PLAYED, 0);
        verify(session).setAttribute(Constants.ATTR_GAMES_LOST, 0);
        verify(session).setAttribute(Constants.ATTR_GAMES_PLAYED, 1);
        verify(session).setAttribute(Constants.ATTR_GAMES_LOST, 1);

        assertEquals(0, GameUtil.getGamesPlayed(session));
        assertEquals(0, GameUtil.getGamesLost(session));
    }
}