package ru.javawebinar.topjava.web.meals;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealsDAO;
import ru.javawebinar.topjava.data.MealsData;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(name = "mealsServlet", urlPatterns = "/meals")
public class MealsServlet extends HttpServlet {

    private MealsDAO mealsDao = new MealsData();

    private static final Logger log = getLogger(MealsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Meals table");
        request.setAttribute("meals", mealsDao.getAllMeal());
        request.getRequestDispatcher("meals/meals.jsp").forward(request, response);
    }
}
