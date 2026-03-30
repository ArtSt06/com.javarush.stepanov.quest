package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static util.Constants.*;

class MainControllerTest {

    private MainController controller;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        controller = new MainController();
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(req.getContextPath()).thenReturn("");
    }

    @Test
    void shouldStartGameAndRedirect() throws ServletException, IOException {
        when(req.getParameter(ATTR_PLAYER_NAME)).thenReturn("Artem");

        controller.doPost(req, resp);

        verify(session).setAttribute(ATTR_PLAYER_NAME, "Artem");
        verify(session).setAttribute(ATTR_CURRENT_QUESTION_ID, START_QUESTION_ID);
        verify(resp).sendRedirect("/game");
    }

    @Test
    void shouldGoToNextQuestion() throws ServletException, IOException {
        when(req.getParameter(ATTR_PLAYER_NAME)).thenReturn(null);
        when(session.getAttribute(ATTR_CURRENT_QUESTION_ID)).thenReturn(1);
        when(req.getParameter(ATTR_ANSWER)).thenReturn("0");

        controller.doPost(req, resp);

        verify(session).setAttribute(ATTR_CURRENT_QUESTION_ID, 2);
        verify(resp).sendRedirect("/game");
    }

    @Test
    void shouldShowLoseResult() throws ServletException, IOException {
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher("/WEB-INF/views/result.jsp")).thenReturn(dispatcher);

        when(req.getParameter(ATTR_PLAYER_NAME)).thenReturn(null);
        when(session.getAttribute(ATTR_CURRENT_QUESTION_ID)).thenReturn(2);
        when(req.getParameter(ATTR_ANSWER)).thenReturn("1");

        controller.doPost(req, resp);

        verify(session).setAttribute(ATTR_GAMES_PLAYED, 1);
        verify(session).setAttribute(ATTR_GAMES_LOST, 1);
        verify(req).setAttribute(eq(ATTR_RESULT_MESSAGE), anyString());
        verify(dispatcher).forward(eq(req), eq(resp));
    }
}