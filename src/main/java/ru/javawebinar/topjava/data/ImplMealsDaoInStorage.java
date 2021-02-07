package ru.javawebinar.topjava.data;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public enum ImplMealsDaoInStorage implements MealsDao {
    INSTANCE;
    private Map<Integer, Meal> mealsStorage = new ConcurrentHashMap<>();

    private static final Logger log = getLogger(ImplMealsDaoInStorage.class);

    private AtomicInteger counter = new AtomicInteger(0);

    {
        Arrays
                .asList(
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.FEBRUARY, 1, 11, 8), "Завтрак", 532),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.FEBRUARY, 1, 14, 20), "Обед", 1000),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 41), "Ужин", 461),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.FEBRUARY, 1, 22, 30), "Поздний ужин", 371),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.FEBRUARY, 2, 0, 0), "Еда на граничное значение", 100),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.FEBRUARY, 2, 9, 21), "Завтрак", 1000),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.FEBRUARY, 2, 14, 32), "Обед", 500),
                        new Meal(counter.getAndIncrement(), LocalDateTime.of(2020, Month.FEBRUARY, 2, 19, 45), "Ужин", 410)
                )
                .forEach(meal -> mealsStorage.put(meal.getId(), meal)
        );
    }

    @Override
    public Meal create(Meal meal) {
        int id = counter.getAndIncrement();
        try {
            Field field = meal.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(meal, id);
            return mealsStorage.put(meal.getId(), meal);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.warn("Not set id to meal [" + meal.getId() + "]");
        } catch (NullPointerException e) {
            log.warn("Meal id [" + meal.getId() + "] not found!");
        }
        return null;
    }

    @Override
    public Meal update(Meal meal) {
        try {
            return mealsStorage.replace(meal.getId(), meal);
        } catch (NullPointerException e) {
            log.warn("Meal id [" + meal.getId() + "] not found!");
        }
        return null;
    }

    @Override
    public void delete(int id) {
        mealsStorage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsStorage.values());
    }

    @Override
    public Meal getById(int id) {
        return mealsStorage.get(id);
    }
}