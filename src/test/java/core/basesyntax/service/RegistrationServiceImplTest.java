package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullUser_notOK() {
        User user = null;
        exception.expect(InvalidUserException.class);
        exception.expectMessage("User can't be null");
        registrationService.register(user);
    }

    @Test
    public void register_rightUser_OK() {
        User user1 = new User();
        user1.setAge(19);
        user1.setPassword("password");
        user1.setLogin("NormalSniklz");
        User user = registrationService.register(user1);
        Assert.assertNotNull(user);
        Assert.assertEquals(user1.hashCode(), user.hashCode());
    }

    @Test
    public void register_userAlreadyExist_notOK() {
        User user1 = new User();
        user1.setAge(19);
        user1.setPassword("password");
        user1.setLogin("Sniklz");
        registrationService.register(user1);
        exception.expect(InvalidUserException.class);
        exception.expectMessage("User already exists");
        registrationService.register(user1);
    }

    @Test
    public void register_nullLogin_notOK() {
        User user1 = new User();
        user1.setAge(18);
        user1.setPassword("password");
        exception.expect(InvalidUserException.class);
        exception.expectMessage("User login can't be null");
        registrationService.register(user1);
    }

    @Test
    public void register_loginLowerThanSix_notOK() {
        User user1 = new User();
        user1.setAge(18);
        user1.setPassword("password");
        user1.setLogin("Foo");
        exception.expect(InvalidUserException.class);
        exception.expectMessage("User login length can't be less than 6 characters");
        registrationService.register(user1);
    }

    @Test
    public void register_passwordLowerThanSix_notOK() {
        User user1 = new User();
        user1.setAge(18);
        user1.setPassword("pas");
        user1.setLogin("Sniklz_short_pass");
        exception.expect(InvalidUserException.class);
        exception.expectMessage("User password must be at least 6 characters");
        registrationService.register(user1);
    }

    @Test
    public void register_nullPassword_notOK() {
        User user1 = new User();
        user1.setAge(18);
        user1.setLogin("Sniklz_null_pass");
        exception.expect(InvalidUserException.class);
        exception.expectMessage("User password can't be null");
        registrationService.register(user1);
    }

    @Test
    public void register_ageLowerThanEighteen_notOK() {
        User user1 = new User();
        user1.setAge(-13);
        user1.setPassword("password");
        user1.setLogin("Sniklz_short_age");
        exception.expect(InvalidUserException.class);
        exception.expectMessage("User age can't be less than 18");
        registrationService.register(user1);
    }

    @Test
    public void register_nullAge_notOK() {
        User user1 = new User();
        user1.setPassword("password");
        user1.setLogin("Sniklz_null_age");
        exception.expect(InvalidUserException.class);
        exception.expectMessage("Age can't be null");
        registrationService.register(user1);
    }
}
