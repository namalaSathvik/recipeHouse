package com.recipehouse;

import java.util.ArrayList;

// This class is our "database". All recipes are stored in an ArrayList.
public class RecipeData {

    private static ArrayList<Recipe> list = new ArrayList<Recipe>();

    // static block runs one time when the class is loaded
    static {
        list.add(new Recipe(1, "Hyderabadi Biryani", "Non-Vegetarian", "Medium", "1 hr 15 min",
            "A fragrant rice dish from Hyderabad, made with basmati rice, chicken, yogurt and aromatic spices.",
            "https://i.pinimg.com/1200x/fa/03/3c/fa033c3d5bef31bc8af5cb39322828c6.jpg",
            "1 kg bone-in chicken|500 g basmati rice (soaked 30 minutes)|2 large onions, fried until golden (birista)|1 cup plain yogurt|1 cup mint and coriander leaves|6 to 10 green chilies|1 ts ginger-garlic paste|Turmeric, red chili, garam masala, coriander and cumin powder|Cardamom, cloves, cinnamon, bay leaf, peppercorns|Pinch of saffron soaked in 2 ts warm milk|2 ts lemon juice|3 to 4 ts ghee and oil|Salt to taste",
            "Heat ghee and oil in a large pan on medium heat.|Add the whole spices and saute for a minute.|Add the onions and fry until golden brown.|Add the ginger-garlic paste and cook for a minute.|Add the chicken pieces and cook until they change colour.|Add the spice powders and cook for 5 minutes.|Add yogurt and cook for 10 minutes.|Add the soaked rice and mix gently.|Cover and cook on low heat for 20 minutes.|Garnish with fried onions, saffron milk and fresh herbs."));

        list.add(new Recipe(2, "Paneer Biryani", "Vegetarian", "Medium", "45-60 minutes",
            "A vegetarian version of the classic biryani made with paneer and aromatic spices.",
            "https://i.pinimg.com/736x/a9/59/c9/a959c9d5f125055f3cd794d814c75c8d.jpg",
            "250 g paneer cubes|2 cups basmati rice|2 onions, sliced|1 cup yogurt|2 tomatoes|Biryani masala|Mint and coriander leaves|Ghee and salt",
            "Soak the rice for 30 minutes and boil it until 70 percent cooked.|Fry the onions until golden and keep half aside.|Add tomatoes, yogurt and masala and cook into a thick gravy.|Add the paneer cubes and cook for 5 minutes.|Layer the gravy and rice in a pot, top with fried onions and herbs.|Cover and cook on low heat for 15 minutes."));

        list.add(new Recipe(3, "Mutton Biryani", "Non-Vegetarian", "Hard", "2 hr 30 min",
            "A rich and slow-cooked biryani made with tender mutton, basmati rice and spices.",
            "https://i.pinimg.com/736x/8f/3d/9d/8f3d9d6031625b6ba5362bf77fe47a43.jpg",
            "1 kg mutton|3 cups basmati rice|3 onions, sliced and fried|1 cup yogurt|Ginger-garlic paste|Whole spices and biryani masala|Mint and coriander leaves|Ghee and salt",
            "Marinate the mutton with yogurt, spices and ginger-garlic paste for 1 hour.|Cook the mutton on low heat until it is 80 percent tender.|Boil the soaked rice with whole spices until 70 percent cooked.|Layer mutton, rice, fried onions and herbs in a heavy pot.|Seal the pot and cook on dum (very low heat) for 30 minutes.|Rest for 10 minutes before opening."));

        list.add(new Recipe(4, "Chole Bhature", "Vegetarian", "Medium", "1 hr 15 min",
            "Spicy chickpeas (chole) served with deep-fried bread (bhature), a North Indian favourite.",
            "https://i.pinimg.com/736x/9d/d7/b5/9dd7b58565004540be75ff5f2a660259.jpg",
            "1 cup chickpeas (soaked overnight)|2 onions|2 tomatoes|Ginger-garlic paste|Chole masala|2 cups maida (flour) for bhature|Yogurt and a pinch of baking soda|Oil for frying",
            "Pressure cook the chickpeas until soft.|Fry onions, ginger-garlic paste and tomatoes with chole masala.|Add the chickpeas and simmer for 15 minutes.|Knead maida with yogurt, baking soda and salt, then rest 1 hour.|Roll into rounds and deep-fry until puffed.|Serve hot chole with bhature."));

        list.add(new Recipe(5, "Paneer Paratha", "Vegetarian", "Easy", "30-40 minutes",
            "A flatbread stuffed with a spiced mixture of paneer and herbs.",
            "https://i.pinimg.com/736x/e9/18/5f/e9185f17a4695fa9a17a78fdb70c12aa.jpg",
            "2 cups whole wheat flour|200 g grated paneer|2 green chilies|Coriander leaves|Red chili powder, garam masala and salt|Ghee or butter",
            "Knead a soft dough with flour, water and salt.|Mix paneer, chilies, coriander and spices for the filling.|Roll a small ball, put filling inside and seal.|Roll gently into a flat paratha.|Cook on a hot tawa with ghee until golden on both sides."));

        list.add(new Recipe(6, "Pav Bhaji", "Vegetarian", "Easy", "30-40 minutes",
            "A famous street food: spicy mashed vegetables (bhaji) served with buttered bread rolls (pav).",
            "https://i.pinimg.com/1200x/a4/3e/39/a43e3921098f715bba22970919a6fa0b.jpg",
            "2 potatoes|1 cup mixed vegetables (cauliflower, peas, carrot)|2 onions|3 tomatoes|Pav bhaji masala|Butter|Pav (bread rolls)",
            "Boil the potatoes and vegetables until soft.|Saute onions and tomatoes in butter.|Add pav bhaji masala and the boiled vegetables.|Mash everything well and cook for 10 minutes.|Toast the pav with butter and serve with the bhaji."));

        list.add(new Recipe(7, "Fried Rice & Manchurian", "Vegetarian", "Medium", "45-60 minutes",
            "A popular Indo-Chinese combo: stir-fried rice with vegetable manchurian balls in a tangy sauce.",
            "https://i.pinimg.com/1200x/a4/62/02/a46202bc73e06474b1fd08e0d708cfb9.jpg",
            "2 cups cooked rice|Cabbage, carrot and beans (chopped)|Soy sauce|Garlic and ginger|Cornflour and maida|Spring onions|Oil for frying",
            "Mix grated vegetables with cornflour and maida and make small balls.|Deep-fry the balls until golden.|Saute garlic and ginger, add soy sauce and a cornflour slurry to make the sauce.|Add the fried balls to the sauce.|Stir-fry vegetables on high heat, add rice and soy sauce.|Serve fried rice with hot manchurian."));

        list.add(new Recipe(8, "Egg Biryani", "Non-Vegetarian", "Medium", "1 hr 15 min",
            "A fragrant biryani made with basmati rice, spices and boiled eggs.",
            "https://i.pinimg.com/736x/9c/9b/f6/9c9bf648a8be3469ded7e5da83fb2e3e.jpg",
            "6 boiled eggs|2 cups basmati rice|2 onions|2 tomatoes|1 cup yogurt|Biryani masala and whole spices|Mint and coriander|Ghee and salt",
            "Boil the eggs, peel them and make small cuts on them.|Fry onions, add tomatoes, yogurt and spices to make the masala.|Add the eggs and cook for 5 minutes.|Boil the rice until 70 percent cooked.|Layer rice and egg masala, add herbs and fried onions.|Cover and cook on low heat for 15 minutes."));

        list.add(new Recipe(9, "Prawns Curry", "Non-Vegetarian", "Medium", "45-60 minutes",
            "A spicy and tangy South Indian curry made with prawns and coconut milk.",
            "https://i.pinimg.com/736x/c7/03/a0/c703a0ceb6479cbf9471de919edbdd3d.jpg",
            "500 g cleaned prawns|1 cup coconut milk|2 onions|2 tomatoes|Garlic and ginger|Turmeric, red chili and coriander powder|Curry leaves|Oil and salt",
            "Marinate the prawns with turmeric and salt for 10 minutes.|Saute onions, garlic, ginger and curry leaves in oil.|Add tomatoes and the spice powders and cook until soft.|Pour in the coconut milk and bring to a gentle boil.|Add the prawns and cook for 5 to 7 minutes only.|Serve with hot rice."));
    }

    // return all recipes
    public static ArrayList<Recipe> getAll() {
        return list;
    }

    // find one recipe by its id (returns null if not found)
    public static Recipe getById(int id) {
        for (Recipe r : list) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }
}
