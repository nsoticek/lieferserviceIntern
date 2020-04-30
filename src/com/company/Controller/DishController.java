package com.company.Controller;

import com.company.DbHelper.DbConnector;
import com.company.DbHelper.DishIngredientRepository;
import com.company.DbHelper.DishRepository;
import com.company.DbHelper.IngredientRepository;
import com.company.models.Dish;
import com.company.models.DishIngredient;
import com.company.models.Ingredient;

import java.util.ArrayList;

public class DishController {

    public static void insertDishAndIngredient(Dish dish) {
        DishRepository dishRepository = new DishRepository();
        IngredientRepository ingredientRepository = new IngredientRepository();
        DishIngredientRepository dishIngredientRepository = new DishIngredientRepository();

        // Create new Dish on DB
        dishRepository.create(dish);
        //Check if ingredient exists; if not create new one and insert it to DB
        checkIfIngredientExistsAndCreateNew(dish, ingredientRepository);
        // Insert the DishID and the IngredientID in table 'dish_ingredient' on DB
        insertDishAndIngredient(dish, ingredientRepository, dishIngredientRepository, dishRepository);
    }

    private static void insertDishAndIngredient(Dish dish, IngredientRepository ingredientRepository,
                                                DishIngredientRepository dishIngredientRepository ,DishRepository dishRepository) {
        // Insert the DishID and the IngredientID in table 'dish_ingredient' on DB
        ArrayList<Ingredient> ingredients = dish.getIngredients();

        // Loop through the ingredients from the current dish and insert it in DB
        for (int i = 0; i < ingredients.size(); i++) {
            // Get IDs from DB and create DishIngredient object
            int dishId = dishRepository.getDishIdFromDb(dish);
            int ingredientId = ingredientRepository.getIngredientIdFromDb(ingredients.get(i).getType());
            DishIngredient dishIngredient = new DishIngredient(dishId, ingredientId);

            dishIngredientRepository.create(dishIngredient);
        }
    }

    public static void checkIfIngredientExistsAndCreateNew(Dish dish, IngredientRepository ingredientRepository) {
        ArrayList<Ingredient> ingredients = dish.getIngredients();

        // Get ID of th current ingredient; if id == 0 --> ingredient does not exist --> insert new
        for (int i = 0; i < ingredients.size(); i++) {
            int id = ingredientRepository.getIngredientIdFromDb(ingredients.get(i).getType());
            if (id == 0) {
                ingredientRepository.create(ingredients.get(i));
            }
        }
    }
}
