package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealsDao;
import ru.javawebinar.topjava.data.ImplMealsDaoInStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {

    private static final Logger log = getLogger(MealsServlet.class);
    private MealsDao mealsDao;
    private static final Integer CALORIES_PER_DAY = 2000;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealsDao = ImplMealsDaoInStorage.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Meals table");
        request.setAttribute("meals", MealsUtil.filteredByStreams(mealsDao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
