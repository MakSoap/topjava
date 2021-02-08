package ru.javawebinar.topjava.data;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealsDao implements MealsDao {
    private static final Logger log = getLogger(InMemoryMealsDao.class);
    private final Map<Integer, Meal> mealsStorage = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    {
        Arrays
                .asList(
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 11, 8), "Завтрак", 532),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 14, 20), "Обед", 1000),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 41), "Ужин", 461),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 22, 30), "Поздний ужин", 371),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 0, 0), "Еда на граничное значение", 100),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 9, 21), "Завтрак", 1000),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 14, 32), "Обед", 500),
                        new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 19, 45), "Ужин", 410)
                )
                .forEach(this::create);
    }

    @Override
    public Meal create(Meal meal) {
        if (meal.getId() == 0) {
            int id = counter.getAndIncrement();
            meal.setId(id);
            mealsStorage.put(meal.getId(), meal);
            log.debug("Meal was added by id [" + meal.getId() + "]");
            return meal;
        } else {
            log.debug("Meal was not created!");
            return null;
        }
    }

    @Override
    public Meal update(Meal meal) {
        Meal mealAfterUpdate = mealsStorage.replace(meal.getId(), meal);
        if (mealAfterUpdate != null) {
            log.debug("Meal was updated");
            return meal;
        } else {
            log.debug("Meal was not updated");
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        return mealsStorage.remove(id) != null;
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