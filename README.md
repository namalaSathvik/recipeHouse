# The Recipe House (Servlet + Tomcat version)

## Folder structure
```
RecipeHouse/
  src/com/recipehouse/   <- Java files (servlets)
  WebContent/            <- HTML, CSS, JS and WEB-INF/web.xml
  build.bat / build.sh   <- compile + copy to Tomcat
```

## Servlets
| URL | Servlet | What it does |
|-----|---------|--------------|
| /search | SearchServlet | search bar + Type/Difficulty filters |
| /recipe?id=1 | RecipeServlet | full recipe page, Start Cooking mode |
| /favorite | FavoriteServlet | save / remove favorites (HttpSession) |
| /random | RandomServlet | "Surprise Me" random recipe |
| /chat | ChatServlet | Chef Bot AI agent |

Other classes: Recipe.java (one recipe), RecipeData.java (all recipes), Layout.java (shared navbar/footer).

## How to run (Tomcat 9)
1. Install JDK 8+ and Apache Tomcat 9.
2. Open `build.bat`, change TOMCAT_HOME to your Tomcat folder.
3. Double-click `build.bat` (compiles the servlets and copies the project to `webapps/RecipeHouse`).
4. Start Tomcat (`bin\startup.bat`).
5. Open http://localhost:8080/RecipeHouse/

Tomcat 10 or newer: change every `javax.servlet` to `jakarta.servlet` in the Java files and in web.xml use the Jakarta namespace.

## Chef Bot (AI agent)
- Works with no setup using keyword rules (ask "ingredients of egg biryani", "easy recipes", "suggest something").
- For real AI answers from Claude: create an API key at console.anthropic.com, set the Windows environment variable
  `ANTHROPIC_API_KEY`, then restart Tomcat. Never write the key inside your code or upload it to GitHub.
