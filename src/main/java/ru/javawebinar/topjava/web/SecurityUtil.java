package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int currentAuthUserid = 1;

    public static int authUserId() {
        return currentAuthUserid;
    }
    public static int setAuthUserId(int userId) {
        return currentAuthUserid = userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}