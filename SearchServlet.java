package com.recipehouse;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Handles the search bar and the filters.  URL example: /search?q=biryani&type=Vegetarian&difficulty=Easy
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // read what the user typed
        String q = request.getParameter("q");
        String type = request.getParameter("type");
        String difficulty = request.getParameter("difficulty");
        if (q == null) q = "";
        if (type == null) type = "";
        if (difficulty == null) difficulty = "";
        q = q.trim().toLowerCase();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(Layout.top("Search"));
        out.println("<h2 class='title'>Search Results</h2>");
        out.println("<div class='cards'>");

        int count = 0;
        for (Recipe r : RecipeData.getAll()) {
            boolean matchText = q.equals("")
                    || r.getName().toLowerCase().contains(q)
                    || r.getDescription().toLowerCase().contains(q);
            boolean matchType = type.equals("") || r.getType().equals(type);
            boolean matchDifficulty = difficulty.equals("") || r.getDifficulty().equals(difficulty);

            if (matchText && matchType && matchDifficulty) {
                out.println(Layout.card(r));
                count++;
            }
        }
        out.println("</div>");

        if (count == 0) {
            out.println("<p class='note'>No recipes found. Try another word, or ask Chef Bot!</p>");
        }
        out.println(Layout.bottom());
    }
}
