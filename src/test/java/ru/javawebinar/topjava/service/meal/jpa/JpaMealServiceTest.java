package ru.javawebinar.topjava.service.meal.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.meal.MealServiceBaseTest;

@ActiveProfiles(Profiles.JPA)
public class JpaMealServiceTest extends MealServiceBaseTest {
}
