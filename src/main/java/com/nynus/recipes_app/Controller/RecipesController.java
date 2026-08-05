package com.nynus.recipes_app.Controller;

import com.nynus.recipes_app.Config.DbConfig;
import com.nynus.recipes_app.Model.Food;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/recipes")
@Component
public class RecipesController {
    /*@Value("${sql.connection}")
    private String myName;*/

    private final List<Food>  foods = new ArrayList<>();

    // Inicialize the class with the recipes for testing
    public RecipesController(){
        foods.add(new Food(1,"Garlic Butter Shrimp Pasta", "spaghetti, shrimp, butter, garlic, olive oil, lemon juice, parsley, red pepper flakes, salt, black pepper", "Cook spaghetti until al dente and reserve a little pasta water. Saute minced garlic in butter and olive oil, then add shrimp and cook until pink. Stir in lemon juice, red pepper flakes, and the pasta, loosening with a splash of pasta water. Season and finish with chopped parsley.", 25, 2));
        foods.add(new Food(2,"Classic Margherita Pizza", "pizza dough, crushed tomatoes, fresh mozzarella, fresh basil, olive oil, salt", "Stretch the dough on a floured surface. Spread with crushed tomatoes, scatter torn mozzarella, and drizzle with olive oil. Bake in a very hot oven until the crust is golden and the cheese bubbles, then top with fresh basil and a pinch of salt.", 40, 4));
        foods.add(new Food(3,"Avocado Toast with Poached Egg", "bread, avocado, egg, lemon juice, chili flakes, salt, black pepper, olive oil", "Toast the bread. Mash the avocado with lemon juice, salt, and pepper, then spread it on the toast. Poach an egg in gently simmering water, set it on top, and finish with chili flakes and a drizzle of olive oil.", 15, 1));
        foods.add(new Food(4,"Chicken Stir-Fry", "chicken breast, bell peppers, broccoli, carrot, soy sauce, garlic, ginger, sesame oil, cornstarch, green onion, rice", "Slice the chicken and toss with cornstarch. Stir-fry it in hot oil until browned and remove, then stir-fry the vegetables with garlic and ginger. Return the chicken, add soy sauce and sesame oil, and toss until coated. Serve over rice topped with green onion.", 30, 3));
        foods.add(new Food(5,"Berry Yogurt Parfait", "Greek yogurt, granola, strawberries, blueberries, honey, mint", "Layer Greek yogurt, granola, and mixed berries in a glass. Repeat the layers, drizzle with honey, and garnish with mint.", 10, 2));
    }

    // Preload the list
    @ModelAttribute(name = "foods")
    public List<Food> getFoods(){
        return foods;
    }

    @GetMapping({"/", "","list"})
    public String index(Model model){
        //model.addAttribute("filter",null);
        //System.out.println(myName);
        return "index";
    }


    @GetMapping("/edit/{id}")
    public String editFood(@PathVariable String id, Model model){
        Food food = foods.stream().filter((e) -> e.getId() == Integer.parseInt(id)).toList().getFirst();
        model.addAttribute("food", food);
        return "form";
    }

    @GetMapping("/new")
    public String newFood(Map<String,Object> model){
        model.put("food", new Food());
        return "form";
    }

    @PostMapping({"/add","/update"})
    public String addFood(Food food){
        //System.out.println(">>> incoming food id = " + food.getId());
        for(int i = 0; i < foods.size(); i++){
            if(Objects.equals(foods.get(i).getId(),food.getId())){
                foods.set(i,food);
                return "redirect:/recipes";
            }
        }
        foods.sort((id1,id2) -> Integer.compare(id1.getId(),id2.getId()));
        food.setId(!foods.isEmpty() ? foods.getLast().getId() + 1 : 1);
        foods.add(food);
        return "redirect:/recipes";
    }

    @GetMapping("/delete/{id}")
    public String deleteFood(@PathVariable int id){
        for(int i = 0; i < foods.size(); i++){
            if(Objects.equals(foods.get(i).getId(),id)){
                foods.remove(foods.get(i));
            }
        }
        for(int i = 0; i < foods.size(); i++){
            Food food =  foods.get(i);
            food.setId(i + 1);
            foods.set(i,food);
        }
        return "redirect:/recipes";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(defaultValue = "") String name, ModelMap model){
        List<Food> filter = new ArrayList<>();
        if(!name.isEmpty() || !foods.isEmpty()){
            filter = foods.stream().filter(food -> food.getName().contains(name)).toList();
        }
        model.replace("foods",filter);
        model.addAttribute("filter", filter);
        return "index";
    }
}
