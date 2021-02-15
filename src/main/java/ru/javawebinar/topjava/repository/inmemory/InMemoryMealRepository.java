package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> mealsRepository2 = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        MealsUtil.meals2.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsRepository2.put(meal.getId(), new ConcurrentHashMap<Integer, Meal>() {{ put(userId, meal); }});
            return meal;
        }
        // handle case: update, but not present in storage
        Map<Integer, Meal> map = mealsRepository2.computeIfPresent(meal.getId(), (id, userMeal) -> {
            userMeal.computeIfPresent(userId, (uId, mealForUser) -> meal);
            return userMeal;
        });
        if (map == null)
            return null;
        else
            return map.getOrDefault(userId, null);
    }

    @Override
    public boolean delete(int id, int userId) {
        return mealsRepository2.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return mealsRepository2.get(id).getOrDefault(userId, null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllByFilter(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllByDateFilter(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return getAllByFilter(userId, meal ->
                DateTimeUtil.isInterval(meal.getDate(), startDate, endDate) &&
                        DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)
        );
    }

    private List<Meal> getAllByFilter(int userId, Predicate<Meal> mealFilter) {
        return mealsRepository2
                .values()
                .stream()
                .filter(userMeal -> userMeal.containsKey(userId))
                .map(userMeal -> userMeal.get(userId))
                .filter(mealFilter)
                .sorted((firstMeal, secondMeal) -> ~firstMeal.getDateTime().compareTo(secondMeal.getDateTime()))
                .collect(Collectors.toList());
    }
}