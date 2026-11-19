package com.recipehouse;

// Helper class so every servlet page has the same top (navbar) and bottom (footer + chatbot).
public class Layout {

    // Makes user text safe before printing it in HTML
    public static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public static String top(String title) {
        return "<!DOCTYPE html>\n<html lang='en'>\n<head>\n"
            + "<meta charset='UTF-8'>\n"
            + "<title>" + esc(title) + " - The Recipe House</title>\n"
            + "<link href='https://fonts.googleapis.com/css2?family=Tangerine:wght@400;700&display=swap' rel='stylesheet'>\n"
            + "<link rel='stylesheet' href='style.css'>\n"
            + "<link rel='stylesheet' href='chatbot.css'>\n"
            + "</head>\n<body>\n"
            + "<nav>\n"
            + "  <h1>The Recipe House</h1>\n"
            + "  <form class='search-bar' action='search' method='get'>\n"
            + "    <input type='text' name='q' placeholder='Search for a recipe...'>\n"
            + "    <button type='submit'>&#128269;</button>\n"
            + "  </form>\n"
            + "  <ul>\n"
            + "    <li><a href='TheRecipe1.html'>Home</a></li>\n"
            + "    <li><a href='TheBook.html'>Recipes</a></li>\n"
            + "    <li><a href='random'>Surprise Me</a></li>\n"
            + "    <li><a href='favorite'>Favorites</a></li>\n"
            + "  </ul>\n"
            + "</nav>\n<main class='page'>\n";
    }

    public static String bottom() {
        return "</main>\n<footer><p>&copy; 2026 The Recipe House. All rights reserved.</p></footer>\n"
            + "<script src='chatbot.js'></script>\n</body>\n</html>";
    }

    // Prints one recipe card (used by search page and favorites page)
    public static String card(Recipe r) {
        return "<div class='card'>\n"
            + "  <a href='recipe?id=" + r.getId() + "'><img src='" + r.getImage() + "' alt='" + esc(r.getName()) + "'></a>\n"
            + "  <h2>" + esc(r.getName()) + "</h2>\n"
            + "  <p>" + esc(r.getType()) + " | " + esc(r.getDifficulty()) + " | " + esc(r.getTime()) + "</p>\n"
            + "  <a class='btn' href='recipe?id=" + r.getId() + "'>View Recipe</a>\n"
            + "</div>\n";
    }
}
