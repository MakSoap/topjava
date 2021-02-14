package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> mealsRepository = new ConcurrentHashMap<>();
    private final Map<Integer, List<Integer>> usersMealRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsRepository.put(meal.getId(), meal);
            usersMealRepository.computeIfPresent(userId, (id, mealList) -> {
                mealList.add(meal.getId());
                return mealList;
            });
            usersMealRepository.computeIfAbsent(userId, (mealList) -> new ArrayList<Integer>() {{ add(meal.getId()); }});
            return meal;
        }
        // handle case: update, but not present in storage
        if (!usersMealRepository.get(userId).contains(meal.getId()))
            return null;
        return mealsRepository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

    }

    @Override
    public boolean delete(int id, int userId) {
        if (!usersMealRepository.get(userId).contains(id))
            return false;
        return mealsRepository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if (!usersMealRepository.get(userId).contains(id))
            return null;
        return mealsRepository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAllByFilter(userId, meal -> true);
    }
    @Override
    public Collection<Meal> getAllByFilter(int userId, Predicate<Meal> filter) {
        List<Integer> mealIdsForUser = usersMealRepository
                .get(userId);
        if (mealIdsForUser == null) {
            return Collections.emptyList();
        } else return mealIdsForUser
                .stream()
                .map( mealId -> get(mealId, userId))
                .filter(Objects::nonNull)
                .filter(filter)
                .sorted((firstMeal, secondMeal) -> ~firstMeal.getDateTime().compareTo(secondMeal.getDateTime()))
                .collect(Collectors.toList());
    }
}