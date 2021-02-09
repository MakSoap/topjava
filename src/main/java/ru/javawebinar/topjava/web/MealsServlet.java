package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealsDao;
import ru.javawebinar.topjava.data.InMemoryMealsDao;
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
    private static final int CALORIES_PER_DAY = 2000;
    private MealsDao mealsDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealsDao = new InMemoryMealsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if (method == null) {
            log.debug("Open meals table");
            request.setAttribute("meals", MealsUtil.filteredByStreams(mealsDao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        } else {
            switch (method) {
                case "add":
                    log.debug("Open add meal form");
                    request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                    break;
                case "edit":
                    log.debug("Open edit meal form");
                    Meal meal = mealsDao.getById(Integer.parseInt(request.getParameter("mealId")));
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                    break;
                case "delete":
                    int mealId = Integer.parseInt(request.getParameter("mealId"));
                    log.debug("Delete meal by id [" + mealId + "] ...");
                    log.debug("Meal [" + mealId + "] was " + (mealsDao.delete(mealId) ? "" : "not") + " removed");
                    response.sendRedirect(request.getServletContext().getContextPath() + "/meals");
                    break;
                default:
                    log.debug("Open meals table");
                    request.setAttribute("meals", MealsUtil.filteredByStreams(mealsDao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                    request.getRequestDispatcher("meals.jsp").forward(request, response);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("datetime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories"))
        );

        String rawMealId = req.getParameter("mealId");
        if (rawMealId != null) {
            log.debug("Editing meal [" + rawMealId + "] ...");
            meal.setId(Integer.parseInt(rawMealId));
            mealsDao.update(meal);
            log.debug("Meal update [" + meal.getId() + "]");
        } else {
            log.debug("Saving new meal...");
            mealsDao.create(meal);
            log.debug("Meal save [" + meal.getId() + "]");
        }
        resp.sendRedirect(req.getServletContext().getContextPath() + "/meals");
    }
}
