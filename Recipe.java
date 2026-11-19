package com.recipehouse;

// A simple class that stores the details of ONE recipe.
public class Recipe {
    private int id;
    private String name;
    private String type;        // "Vegetarian" or "Non-Vegetarian"
    private String difficulty;  // Easy / Medium / Hard
    private String time;
    private String description;
    private String image;
    private String[] ingredients;
    private String[] steps;

    // ingredients and steps are written as one text separated by the | symbol
    public Recipe(int id, String name, String type, String difficulty, String time,
                  String description, String image, String ingredients, String steps) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
        this.time = time;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients.split("\\|");
        this.steps = steps.split("\\|");
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDifficulty() { return difficulty; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public String[] getIngredients() { return ingredients; }
    public String[] getSteps() { return steps; }
}
