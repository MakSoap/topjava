package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 1;

    public static final Meal userMeal100001 = new Meal(START_SEQ + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal100002 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal100003 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal100004 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal100005 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal100006 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal100007 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final Meal adminMeal100008 = new Meal(START_SEQ + 9, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal adminMeal100009 = new Meal(START_SEQ + 10, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final LocalDate betweenInclusive = userMeal100001.getDate();

    public static final Meal newMeal = new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 15, 0), "Ужин", 410);
    public static final Meal duplicateDateTimeUserMeal = new Meal(null, userMeal100007.getDateTime(), "Ужин2", 500);

    public static final List<Meal> userMeals = Arrays.asList(
            userMeal100007,
            userMeal100006,
            userMeal100005,
            userMeal100004,
            userMeal100003,
            userMeal100002,
            userMeal100001
            );

    public static final List<Meal> adminMeals = Arrays.asList(adminMeal100009, adminMeal100008);

    public static final List<Meal> userMealsBetweenInclusive = Arrays.asList(userMeal100003, userMeal100002, userMeal100001);
    public static final List<Meal> userMealsBetweenInclusiveWithoutFilter = userMeals;


    public static Meal getUpdatedForUser() {
        Meal updated = new Meal(userMeals.get(0));
        updated.setDateTime(LocalDateTime.of(2019, Month.MAY, 25, 15, 30));
        updated.setDescription("Чаепитие");
        updated.setCalories(300);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
