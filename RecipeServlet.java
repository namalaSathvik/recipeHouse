package com.recipehouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Shows ONE recipe page.  URL example: /recipe?id=1
@WebServlet("/recipe")
public class RecipeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // get the id from the URL and find the recipe
        Recipe r = null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            r = RecipeData.getById(id);
        } catch (NumberFormatException e) {
            r = null;   // id was missing or not a number
        }

        if (r == null) {
            out.println(Layout.top("Not found"));
            out.println("<p class='note'>Sorry, recipe not found.</p>");
            out.println(Layout.bottom());
            return;
        }

        // is this recipe already in the user's favorites?
        ArrayList<Integer> favs = FavoriteServlet.getFavorites(request.getSession());
        boolean isFav = favs.contains(r.getId());

        out.println(Layout.top(r.getName()));
        out.println("<div class='recipe-box'>");
        out.println("<img class='big' src='" + r.getImage() + "' alt='" + Layout.esc(r.getName()) + "'>");
        out.println("<h2 class='title'>" + Layout.esc(r.getName()) + "</h2>");
        out.println("<p class='info'>" + Layout.esc(r.getType()) + " | Difficulty: " + r.getDifficulty()
                + " | Time: " + r.getTime() + "</p>");
        out.println("<p class='desc'>" + Layout.esc(r.getDescription()) + "</p>");

        // favorite button (a small form that sends id to FavoriteServlet)
        out.println("<form method='post' action='favorite'>");
        out.println("<input type='hidden' name='id' value='" + r.getId() + "'>");
        if (isFav) {
            out.println("<button class='btn' type='submit'>&#9829; Remove from Favorites</button>");
        } else {
            out.println("<button class='btn' type='submit'>&#9825; Save to Favorites</button>");
        }
        out.println("</form>");

        // ingredients
        out.println("<h3>Ingredients</h3>");
        for (String item : r.getIngredients()) {
            out.println("<div class='ingredient'>" + Layout.esc(item) + "</div>");
        }

        // steps
        out.println("<h3>Method</h3>");
        int number = 1;
        for (String step : r.getSteps()) {
            out.println("<p class='step'>" + number + ". " + Layout.esc(step) + "</p>");
            number++;
        }

        // Start Cooking mode: shows one step at a time
        out.println("<button class='btn' type='button' onclick='startCooking()'>&#10022; Start Cooking &#10022;</button>");
        out.println("<div id='cookBox'>");
        out.println("  <p id='stepText'></p>");
        out.println("  <button class='btn' type='button' onclick='prevStep()'>Back</button>");
        out.println("  <button class='btn' type='button' onclick='nextStep()'>Next</button>");
        out.println("</div>");
        out.println("<p><a class='btn' href='TheBook.html'>Back to Recipes</a></p>");
        out.println("</div>");

        out.println("<script>");
        out.println("var steps = document.getElementsByClassName('step');");
        out.println("var current = 0;");
        out.println("function showStep() {");
        out.println("  document.getElementById('stepText').innerText = 'Step ' + (current + 1) + ' of ' + steps.length + ' -> ' + steps[current].innerText;");
        out.println("}");
        out.println("function startCooking() {");
        out.println("  document.getElementById('cookBox').style.display = 'block';");
        out.println("  current = 0;");
        out.println("  showStep();");
        out.println("}");
        out.println("function nextStep() { if (current < steps.length - 1) { current++; showStep(); } }");
        out.println("function prevStep() { if (current > 0) { current--; showStep(); } }");
        out.println("</script>");
        out.println(Layout.bottom());
    }
}
