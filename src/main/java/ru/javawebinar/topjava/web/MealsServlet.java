package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealsDao;
import ru.javawebinar.topjava.data.MealsDaoInStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {

    private static final Logger log = getLogger(MealsServlet.class);
    private static final Integer CALORIES_PER_DAY = 2000;
    private MealsDao mealsDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealsDao = MealsDaoInStorage.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if (method == null) {
            log.debug("Meals table");
            request.setAttribute("meals", MealsUtil.filteredByStreams(mealsDao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        switch (method) {
            case "add":
                log.debug("Open edit meal form");
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            case "edit":
                log.debug("Open edit meal form");
                String rawMealId = request.getParameter("mealId");
                int mealId = Integer.parseInt(rawMealId);
                Meal meal = mealsDao.getById(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            case "delete":
                rawMealId = request.getParameter("mealId");
                log.debug("Delete meal by id [" + rawMealId + "] ...");
                mealId = Integer.parseInt(rawMealId);
                boolean resultOfDelete = mealsDao.delete(mealId);
                log.debug("Meal [" + mealId + "] was " + (resultOfDelete ? "" : "not") + " removed");
                response.sendRedirect(request.getServletContext().getContextPath() + "/meals");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");

        LocalDateTime datetime = LocalDateTime.parse(req.getParameter("datetime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        switch (method) {
            case "add":
                log.debug("Saving new meal...");
                Meal meal = new Meal(datetime, description, calories);
                mealsDao.create(meal);
                log.debug("Meal save [" + meal.getId() + "]");
                break;
            case "edit":
                String rawMealId = req.getParameter("mealId");
                log.debug("Editing meal [" + rawMealId + "] ...");
                int mealId = Integer.parseInt(rawMealId);
                meal = new Meal(mealId, datetime, description, calories);
                mealsDao.update(meal);
                log.debug("Meal update [" + meal.getId() + "]");
                break;
        }
        resp.sendRedirect(req.getServletContext().getContextPath() + "/meals");
    }
}
