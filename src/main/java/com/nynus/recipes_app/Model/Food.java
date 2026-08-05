package com.nynus.recipes_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private int id;
    private String name;
    private String ingredients;
    private String preparation;
    private Integer prep_time;
    private Integer servings;
}
