package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDao {

    Meal create(Meal meal);
    Meal update(Meal meal);
    void delete(int id);

    List<Meal> getAll();
    Meal getById(int id);


}
