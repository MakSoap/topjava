package ru.javawebinar.topjava.service.user.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.user.UserServiceBaseTest;

@ActiveProfiles(Profiles.JDBC)
public class JdbcUserServiceTest extends UserServiceBaseTest {
}
