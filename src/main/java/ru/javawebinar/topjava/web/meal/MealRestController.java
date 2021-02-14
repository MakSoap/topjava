package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<MealTo> getAll() {
        log.info("Get all");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Collection<MealTo> getAllByFilter(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("get all by filter");
        return MealsUtil.getTos(
                service.getAllByFilter(
                        SecurityUtil.authUserId(),
                        meal ->
                                DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate) &&
                                        DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)
                ),
                SecurityUtil.authUserCaloriesPerDay()
        );
    }

    public MealTo get(int id) {
        log.info("Get by id {}", id);
        return MealsUtil.getTos(
                Collections.singleton(service.get(id, SecurityUtil.authUserId())),
                SecurityUtil.authUserCaloriesPerDay()
        ).get(0);
    }
    public void delete(int id) {
        log.info("Delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }
    public MealTo create(Meal meal) {
        log.info("Create {}", meal);
        ValidationUtil.checkNew(meal);
        return MealsUtil.getTos(
                Collections.singleton(service.create(meal, SecurityUtil.authUserId())),
                SecurityUtil.authUserCaloriesPerDay()
        ).get(0);
    }
    public void update(Meal meal, int id) {
        log.info("Update {}", meal);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}