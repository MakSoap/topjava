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

public class AddMeal extends HttpServlet {

    private static final Logger log = getLogger(AddMeal.class);
    private MealsDao mealsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        mealsDao = ImplMealsDaoInStorage.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Open edit meal form");
        req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("Saving new meal...");
        String datetime = req.getParameter("datetime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        Meal meal = new Meal(LocalDate.parse(datetime).atStartOfDay(), description, Integer.parseInt(calories));
        mealsDao.create(meal);
        log.debug("Meal save [" + meal.getId() + "]");
        resp.sendRedirect(req.getServletContext().getContextPath() + "/meals");
    }

}
