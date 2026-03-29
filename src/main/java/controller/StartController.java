package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.QuestData;

import java.io.IOException;

import static util.Constants.ATTR_INTRO;

@WebServlet("/start")
public class StartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ATTR_INTRO, QuestData.getIntro());
        req.getRequestDispatcher("/WEB-INF/views/start.jsp").forward(req, resp);
    }
}