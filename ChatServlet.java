package com.recipehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// The AI chat agent "Chef Bot".
// 1) If the environment variable ANTHROPIC_API_KEY is set, it asks the Claude AI.
// 2) If not (or if something fails), it uses simple keyword rules so it ALWAYS works.
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String message = request.getParameter("message");
        if (message == null) message = "";

        String reply = null;

        // try the real AI first (only if a key exists)
        String apiKey = System.getenv("ANTHROPIC_API_KEY");
        if (apiKey != null && !apiKey.equals("") && !message.trim().equals("")) {
            try {
                reply = askClaude(apiKey, message);
            } catch (Exception e) {
                System.out.println("Claude API failed, using simple bot: " + e.getMessage());
            }
        }

        // fall back to the simple rule based bot
        if (reply == null || reply.equals("")) {
            reply = simpleBot(message);
        }

        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().print(reply);
    }

    // ---------- Part 1: call the Claude API ----------
    private String askClaude(String apiKey, String userMessage) throws IOException {

        // tell the AI who it is and which recipes our website has
        String system = "You are Chef Bot, a friendly cooking assistant on a college project website called The Recipe House. "
                + "Answer in at most 4 short sentences. Focus on Indian food. The website has these recipes: ";
        for (Recipe r : RecipeData.getAll()) {
            system = system + r.getName() + ", ";
        }

        String json = "{\"model\":\"claude-sonnet-5-5\",\"max_tokens\":300,"
                + "\"system\":\"" + jsonEscape(system) + "\","
                + "\"messages\":[{\"role\":\"user\",\"content\":\"" + jsonEscape(userMessage) + "\"}]}";

        URL url = new URL("https://api.anthropic.com/v1/messages");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("content-type", "application/json");
        con.setRequestProperty("x-api-key", apiKey);
        con.setRequestProperty("anthropic-version", "2023-06-01");
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        if (con.getResponseCode() != 200) {
            throw new IOException("HTTP " + con.getResponseCode());
        }

        // read the whole answer
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        return getTextFromJson(sb.toString());
    }

    // makes text safe to put inside a JSON string
    private String jsonEscape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "").replace("\t", " ");
    }

    // very simple way to read the "text" value from the JSON answer (no extra library needed)
    private String getTextFromJson(String json) {
        int start = json.indexOf("\"text\":\"");
        if (start == -1) return null;
        start = start + 8;

        StringBuilder result = new StringBuilder();
        int i = start;
        while (i < json.length()) {
            char c = json.charAt(i);
            if (c == '\\') {                       // escaped character like \n or \"
                char next = json.charAt(i + 1);
                if (next == 'n') result.append('\n');
                else if (next == '"') result.append('"');
                else if (next == '\\') result.append('\\');
                else result.append(next);
                i = i + 2;
            } else if (c == '"') {                 // end of the text
                break;
            } else {
                result.append(c);
                i++;
            }
        }
        return result.toString();
    }

    // ---------- Part 2: simple keyword bot ----------
    private String simpleBot(String message) {
        String m = message.toLowerCase().trim();

        if (m.equals("")) {
            return "Please type a question, for example: 'ingredients of egg biryani'.";
        }

        // 1) did the user mention a recipe name?
        Recipe found = null;
        for (Recipe r : RecipeData.getAll()) {
            if (m.contains(r.getName().toLowerCase())) {
                found = r;
            }
        }
        if (found != null) {
            return aboutRecipe(found, m);
        }

        // 2) greetings and thanks
        if (m.equals("hi") || m.startsWith("hi ") || m.contains("hello") || m.contains("hey")) {
            return "Hello! I am Chef Bot. Ask me for a recipe, ingredients, easy dishes or a surprise!";
        }
        if (m.contains("thank")) {
            return "You are welcome! Happy cooking!";
        }

        // 3) lists by type / difficulty
        if (m.contains("non") && m.contains("veg")) {
            return listRecipes("Non-vegetarian recipes", "type", "Non-Vegetarian");
        }
        if (m.contains("veg")) {
            return listRecipes("Vegetarian recipes", "type", "Vegetarian");
        }
        if (m.contains("easy") || m.contains("quick") || m.contains("fast") || m.contains("beginner")) {
            return listRecipes("Easy recipes", "difficulty", "Easy");
        }
        if (m.contains("hard") || m.contains("difficult")) {
            return listRecipes("Hard recipes", "difficulty", "Hard");
        }

        // 4) suggestion
        if (m.contains("suggest") || m.contains("recommend") || m.contains("surprise") || m.contains("what should")) {
            ArrayList<Recipe> all = RecipeData.getAll();
            Recipe r = all.get(new Random().nextInt(all.size()));
            return "How about " + r.getName() + "? " + r.getDescription() + " Open it with the search bar!";
        }

        // 5) substitute tips
        if (m.contains("substitute") || m.contains("replace") || m.contains("instead")) {
            if (m.contains("yogurt") || m.contains("curd")) return "No yogurt? Use buttermilk or a little lemon juice with cream.";
            if (m.contains("paneer")) return "No paneer? Firm tofu works well.";
            if (m.contains("saffron")) return "No saffron? Use a tiny pinch of turmeric in warm milk for colour.";
            if (m.contains("ghee")) return "No ghee? Use butter or a neutral oil.";
            return "Tell me which ingredient you want to replace (yogurt, paneer, saffron or ghee).";
        }

        return "I did not understand that. Try: 'ingredients of pav bhaji', 'how to make mutton biryani', "
                + "'easy recipes', 'vegetarian recipes' or 'suggest something'.";
    }

    // answer a question about one recipe
    private String aboutRecipe(Recipe r, String m) {
        if (m.contains("ingredient") || m.contains("need")) {
            String text = "Ingredients for " + r.getName() + ":\n";
            for (String item : r.getIngredients()) {
                text = text + "- " + item + "\n";
            }
            return text;
        }
        if (m.contains("step") || m.contains("how") || m.contains("make") || m.contains("method")) {
            String text = "How to make " + r.getName() + ":\n";
            int n = 1;
            for (String step : r.getSteps()) {
                text = text + n + ". " + step + "\n";
                n++;
            }
            return text;
        }
        if (m.contains("time") || m.contains("long")) {
            return r.getName() + " takes about " + r.getTime() + ".";
        }
        return r.getName() + ": " + r.getDescription() + " Difficulty: " + r.getDifficulty()
                + ", Time: " + r.getTime() + ". Ask me for the ingredients or the steps!";
    }

    // makes a list of recipe names for a type or difficulty
    private String listRecipes(String heading, String field, String value) {
        String text = heading + ":\n";
        for (Recipe r : RecipeData.getAll()) {
            String actual = field.equals("type") ? r.getType() : r.getDifficulty();
            if (actual.equals(value)) {
                text = text + "- " + r.getName() + " (" + r.getTime() + ")\n";
            }
        }
        return text;
    }
}
