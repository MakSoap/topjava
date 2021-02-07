package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealsDao;
import ru.javawebinar.topjava.data.ImplMealsDaoInStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class DeleteMeal extends HttpServlet {

    private static final Logger log = getLogger(DeleteMeal.class);
    private MealsDao mealsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        mealsDao = ImplMealsDaoInStorage.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rawMealId = req.getParameter("mealId");
        log.debug("Delete meal by id [" + rawMealId + "] ...");
        int mealId = Integer.parseInt(rawMealId);
        mealsDao.delete(mealId);
        log.debug("Meal [" + mealId + "] was removed");
        resp.sendRedirect(req.getServletContext().getContextPath() + "/meals");
    }
}
