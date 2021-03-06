package Servlets;

import Game.GameController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "upgrade", urlPatterns = "/upgrade")
public class UpgradeServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String baseID = req.getParameter("baseID");
        String username = req.getParameter("username");
        int baseIdAsInt = Integer.parseInt(baseID);

        GameController.getInstance().upgrade(baseIdAsInt, username);
        String gameState = GameController.getInstance().getState(username);

        PrintWriter out = resp.getWriter();
        out.print(gameState);
    }
}
