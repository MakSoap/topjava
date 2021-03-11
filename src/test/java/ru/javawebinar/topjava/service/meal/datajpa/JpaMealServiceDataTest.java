package ru.javawebinar.topjava.service.meal.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.meal.MealServiceBaseTest;

@ActiveProfiles(Profiles.DATAJPA)
public class JpaMealServiceDataTest extends MealServiceBaseTest {
}
