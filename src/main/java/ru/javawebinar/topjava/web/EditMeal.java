package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealsDao;
import ru.javawebinar.topjava.data.ImplMealsDaoInStorage;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static org.slf4j.LoggerFactory.getLogger;

public class EditMeal extends HttpServlet {

    private static final Logger log = getLogger(EditMeal.class);
    private MealsDao mealsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        mealsDao = ImplMealsDaoInStorage.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Open edit meal form");
        String rawMealId = req.getParameter("mealId");
        int mealId = Integer.parseInt(rawMealId);
        Meal meal = mealsDao.getById(mealId);
        req.setAttribute("datetime", meal.getDateTime());
        req.setAttribute("description", meal.getDescription());
        req.setAttribute("calories", meal.getCalories());
        req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String rawMealId = req.getParameter("mealId");
        log.debug("Editing meal [" + rawMealId + "] ...");
        int mealId = Integer.parseInt(rawMealId);
        String datetime = req.getParameter("datetime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        Meal meal = new Meal(mealId, LocalDate.parse(datetime).atStartOfDay(), description, Integer.parseInt(calories));
        mealsDao.update(meal);
        log.debug("Meal update [" + meal.getId() + "]");
        resp.sendRedirect(req.getServletContext().getContextPath() + "/meals");
    }
}
