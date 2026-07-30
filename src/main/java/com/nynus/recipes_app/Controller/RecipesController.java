package com.nynus.recipes_app.Controller;

import com.nynus.recipes_app.Model.Food;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/recipes")
public class RecipesController {
    private final List<Food> foods = new ArrayList<>();

    // Inicialize the class with the recipes for testing
    public RecipesController(){
        foods.add(new Food("Garlic Butter Shrimp Pasta", "spaghetti, shrimp, butter, garlic, olive oil, lemon juice, parsley, red pepper flakes, salt, black pepper", "Cook spaghetti until al dente and reserve a little pasta water. Saute minced garlic in butter and olive oil, then add shrimp and cook until pink. Stir in lemon juice, red pepper flakes, and the pasta, loosening with a splash of pasta water. Season and finish with chopped parsley.", 25, 2));
        foods.add(new Food("Classic Margherita Pizza", "pizza dough, crushed tomatoes, fresh mozzarella, fresh basil, olive oil, salt", "Stretch the dough on a floured surface. Spread with crushed tomatoes, scatter torn mozzarella, and drizzle with olive oil. Bake in a very hot oven until the crust is golden and the cheese bubbles, then top with fresh basil and a pinch of salt.", 40, 4));
        foods.add(new Food("Avocado Toast with Poached Egg", "bread, avocado, egg, lemon juice, chili flakes, salt, black pepper, olive oil", "Toast the bread. Mash the avocado with lemon juice, salt, and pepper, then spread it on the toast. Poach an egg in gently simmering water, set it on top, and finish with chili flakes and a drizzle of olive oil.", 15, 1));
        foods.add(new Food("Chicken Stir-Fry", "chicken breast, bell peppers, broccoli, carrot, soy sauce, garlic, ginger, sesame oil, cornstarch, green onion, rice", "Slice the chicken and toss with cornstarch. Stir-fry it in hot oil until browned and remove, then stir-fry the vegetables with garlic and ginger. Return the chicken, add soy sauce and sesame oil, and toss until coated. Serve over rice topped with green onion.", 30, 3));
        foods.add(new Food("Berry Yogurt Parfait", "Greek yogurt, granola, strawberries, blueberries, honey, mint", "Layer Greek yogurt, granola, and mixed berries in a glass. Repeat the layers, drizzle with honey, and garnish with mint.", 10, 2));

    }
    // Preload the list
    @ModelAttribute(name = "foods")
    public List<Food> getFoods(){
        return foods;
    }


    @GetMapping({"/", "","list"})
    public String index(Model model){
        model.addAttribute("filter",new ArrayList<Food>());
        return "index";
    }

    @GetMapping("/new")
    public String newFood(Map<String,Object> model){
        model.put("food", new Food());
        return "new";
    }

    @PostMapping("/add")
    public String addFood(Food food){
        foods.add(food);
        return "redirect:/recipes";
    }


    @GetMapping("/filter")
    public String filter(@RequestParam(defaultValue = "") String name, ModelMap model){
        List<Food> filter = new ArrayList<>();
        if(!name.isEmpty() || foods.size() > 1){
            filter = foods.stream().filter(food -> food.getName().contains(name)).toList();
        }
        model.addAttribute("filter", filter);
        return "index";
    }

}
