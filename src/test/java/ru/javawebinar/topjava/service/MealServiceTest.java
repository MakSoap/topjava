package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(MealTestData.NEW);
        Meal created = service.create(MealTestData.NEW, UserTestData.USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);

        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeInUser() {
        assertThrows(DataAccessException.class, () ->
                service.create(MealTestData.DUPLICATE_DATE_TIME_USER_MEAL, UserTestData.USER_ID)
        );
        assertThat(service.create(MealTestData.DUPLICATE_DATE_TIME_USER_MEAL, UserTestData.ADMIN_ID)).isNotNull();
    }

    @Test
    public void get() {
        Meal userMeal = service.get(MealTestData.USER_MEAL_100001.getId(), UserTestData.USER_ID);
        MealTestData.assertMatch(userMeal, MealTestData.USER_MEAL_100001);
    }

    @Test
    public void getAlienMeal() {
        assertThrows(NotFoundException.class, () ->
                service.get(MealTestData.USER_MEAL_100001.getId(), UserTestData.ADMIN_ID)
        );
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(MealTestData.NOT_FOUND, UserTestData.USER_ID)
        );
    }

    @Test
    public void getBetweenDateInclusive() {
        MealTestData.assertMatch(
                service.getBetweenInclusive(MealTestData.BETWEEN_INCLUSIVE, MealTestData.BETWEEN_INCLUSIVE, UserTestData.USER_ID),
                MealTestData.userMealsBetweenInclusive
        );
    }
    @Test
    public void getBetweenInclusiveWithoutDate() {
        MealTestData.assertMatch(
                service.getBetweenInclusive(null, null, UserTestData.USER_ID),
                MealTestData.userMealsBetweenInclusiveWithoutFilter
        );
    }

    @Test
    public void getAll() {
        List<Meal> userMeals = service.getAll(UserTestData.USER_ID);
        MealTestData.assertMatch(userMeals, MealTestData.userMeals);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdatedForUser();
        service.update(updated, UserTestData.USER_ID);
        MealTestData.assertMatch(service.get(updated.getId(), UserTestData.USER_ID), MealTestData.getUpdatedForUser());
    }

    @Test
    public void updateNotFound() {
        Meal notFoundMeal = new Meal(MealTestData.USER_MEAL_100001);
        notFoundMeal.setId(MealTestData.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(notFoundMeal, UserTestData.ADMIN_ID));
    }

    @Test
    public void updateAlienMeal() {
        Meal alienMeal = MealTestData.getUpdatedForUser();
        assertThrows(NotFoundException.class, () -> service.update(alienMeal, UserTestData.ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MealTestData.USER_MEAL_100001.getId(), UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.USER_MEAL_100001.getId(), UserTestData.USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    public void deleteAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.USER_MEAL_100001.getId(), UserTestData.ADMIN_ID));
    }
}