package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("Get all");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllByFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("get all by filter");
        startDate = startDate == null ? LocalDate.MIN : startDate;
        endDate = endDate == null ? LocalDate.MAX : endDate;
        startTime = startTime == null ? LocalTime.MIN : startTime;
        endTime = endTime == null ? LocalTime.MAX : endTime;
        return MealsUtil.getTos(
                service.getAllByFilter(
                        SecurityUtil.authUserId(),
                        startDate,
                        endDate,
                        startTime,
                        endTime
                ),
                SecurityUtil.authUserCaloriesPerDay()
        );
    }

    public Meal get(int id) {
        log.info("Get by id {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }
    public void delete(int id) {
        log.info("Delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }
    public Meal create(Meal meal) {
        log.info("Create {}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }
    public void update(Meal meal, int id) {
        log.info("Update {}", meal);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}