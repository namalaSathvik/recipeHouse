package com.recipehouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Saves favorite recipes in the HttpSession (so they stay while the browser is open).
@WebServlet("/favorite")
public class FavoriteServlet extends HttpServlet {

    // gets the favorites list from the session (creates an empty one the first time)
    @SuppressWarnings("unchecked")
    public static ArrayList<Integer> getFavorites(HttpSession session) {
        ArrayList<Integer> favs = (ArrayList<Integer>) session.getAttribute("favorites");
        if (favs == null) {
            favs = new ArrayList<Integer>();
            session.setAttribute("favorites", favs);
        }
        return favs;
    }

    // POST = add or remove a recipe
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            ArrayList<Integer> favs = getFavorites(request.getSession());
            if (favs.contains(id)) {
                favs.remove(id);   // already saved -> remove
            } else {
                favs.add(id);      // not saved -> add
            }
            response.sendRedirect("recipe?id=" + id);
        } catch (NumberFormatException e) {
            response.sendRedirect("TheBook.html");
        }
    }

    // GET = show the favorites page
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ArrayList<Integer> favs = getFavorites(request.getSession());

        out.println(Layout.top("Favorites"));
        out.println("<h2 class='title'>&#9829; My Favorite Recipes</h2>");
        if (favs.isEmpty()) {
            out.println("<p class='note'>You have not saved any recipe yet.</p>");
        } else {
            out.println("<div class='cards'>");
            for (Integer id : favs) {
                Recipe r = RecipeData.getById(id);
                if (r != null) out.println(Layout.card(r));
            }
            out.println("</div>");
        }
        out.println(Layout.bottom());
    }
}
