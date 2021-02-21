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
        Meal newMeal = new Meal(MealTestData.getNew());
        Meal created = service.create(MealTestData.getNew(), MealTestData.USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);

        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, MealTestData.USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeInUser() {
        assertThrows(DataAccessException.class, () ->
                service.create(MealTestData.getDuplicateDateTimeInUser(), MealTestData.USER_ID)
        );
        assertThat(service.create(MealTestData.getDuplicateDateTimeInUser(), MealTestData.ADMIN_ID)).isNotNull();
    }

    @Test
    public void get() {
        Meal userMeal = service.get(MealTestData.userMealForGet.getId(), MealTestData.USER_ID);
        MealTestData.assertMatch(userMeal, MealTestData.userMealForGet);
    }

    @Test
    public void getAlienMeal() {
        assertThrows(NotFoundException.class, () ->
                service.get(MealTestData.userMealForGet.getId(), MealTestData.ADMIN_ID)
        );
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(MealTestData.NOT_FOUND, MealTestData.USER_ID)
        );
    }

    @Test
    public void getBetweenDateInclusive() {
        MealTestData.assertMatch(
                service.getBetweenInclusive(MealTestData.BETWEEN_INCLUSIVE, MealTestData.BETWEEN_INCLUSIVE, MealTestData.USER_ID),
                MealTestData.userMealBetweenInclusive
        );
    }

    @Test
    public void getAll() {
        List<Meal> userMeals = service.getAll(MealTestData.USER_ID);
        MealTestData.assertMatch(userMeals, MealTestData.userMeal);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdatedForUser();
        service.update(updated, MealTestData.USER_ID);
        MealTestData.assertMatch(service.get(updated.getId(), MealTestData.USER_ID), MealTestData.getUpdatedForUser());
    }

    @Test
    public void updateNotFound() {
        Meal notFoundMeal = new Meal(MealTestData.userMealForGet);
        notFoundMeal.setId(MealTestData.NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(notFoundMeal, MealTestData.ADMIN_ID));
    }

    @Test
    public void updateAlienMeal() {
        Meal alienMeal = MealTestData.getUpdatedForUser();
        assertThrows(NotFoundException.class, () -> service.update(alienMeal, MealTestData.ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MealTestData.userMealForDelete, MealTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.userMealForDelete, MealTestData.USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.NOT_FOUND, MealTestData.USER_ID));
    }

    @Test
    public void deleteAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MealTestData.userMeal.get(0).getId(), MealTestData.ADMIN_ID));
    }
}