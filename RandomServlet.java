package com.recipehouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// "Surprise Me" button: sends the user to a random recipe.
@WebServlet("/random")
public class RandomServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Recipe> all = RecipeData.getAll();
        Random random = new Random();
        Recipe r = all.get(random.nextInt(all.size()));
        response.sendRedirect("recipe?id=" + r.getId());
    }
}
