package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 0;
    public static final LocalDate BETWEEN_INCLUSIVE = LocalDate.of(2020, Month.JANUARY, 30);

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static final List<Meal> userMeal = Arrays.asList(
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500)
    );

    public static final List<Meal> userMealBetweenInclusive = userMeal.subList(4, 7);
    public static final Meal userMealForGet = userMeal.get(6);
    public static final int userMealForDelete = userMeal.get(0).getId();

    public static final List<Meal> adminMeal = Arrays.asList(
            new Meal(9, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500),
            new Meal(8, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510)
    );

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 15, 0), "Ужин", 410);
    }

    public static Meal getDuplicateDateTimeInUser() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин2", 500);
    }

    public static Meal getUpdatedForUser() {
        Meal updated = new Meal(userMeal.get(0));
        updated.setDateTime(LocalDateTime.of(2019, Month.MAY, 25, 15, 30));
        updated.setDescription("Чаепитие");
        updated.setCalories(300);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual.toString()).isEqualTo(expected.toString());
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(mapToString(actual)).isEqualTo(mapToString(expected));
    }

    private static List<String> mapToString(Iterable<Meal> meals) {
        return Stream.of(meals).map(Object::toString).collect(Collectors.toList());
    }
}
