package ru.javawebinar.topjava.data;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsData implements MealsDAO {
    private static ConcurrentHashMap<Integer, Meal> mealsConcurrentHashMap = new ConcurrentHashMap<>();

    private static final Logger log = getLogger(MealsData.class);

    private static AtomicInteger counter = new AtomicInteger(0);

    static  {
        Stream.of(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        )
                .forEach(meal -> {
                    log.debug("Add meal by id: " + meal.getId());
                    mealsConcurrentHashMap.put(meal.getId(), meal);
                }
        );
    }

    @Override
    public void createMeal(Meal meal) {
        mealsConcurrentHashMap.put(counter.getAndIncrement(), meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealsConcurrentHashMap.replace(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(int id) {
        mealsConcurrentHashMap.remove(id);
    }

    @Override
    public List<MealTo> getAllMeal() {
        return MealsUtil
                .filteredByStreams(
                        new ArrayList<>(mealsConcurrentHashMap.values()),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        2000
                );
    }

    @Override
    public MealTo getMealById(int id) {
        return MealsUtil
                .filteredByStreams(
                        Arrays.asList(mealsConcurrentHashMap.get(id)),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        2000
                )
                .stream()
                .findFirst()
                .orElse(null);
    }


}
