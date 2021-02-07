package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealsDAO {

    void createMeal(Meal meal);
    void updateMeal(Meal meal);
    void deleteMeal(int id);

    List<MealTo> getAllMeal();
    MealTo getMealById(int id);


}
