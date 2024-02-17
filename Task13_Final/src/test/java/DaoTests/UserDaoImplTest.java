package DaoTests;

import Task13.Dao.Impl.*;
import Task13.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest {

    private UserDaoImpl userDaoImpl;

    private static Long current = 33L;

    @BeforeEach
    public void setUp() {
        userDaoImpl = new UserDaoImpl();
    }

    @Test
    public void saveAndGetUserPositiveTest() {
        User newUser = new User();
		newUser.setEmail("all33@gmail.com");
		newUser.setUsername("Alex");
		newUser.setUsersurname("Flag2");
        newUser.setUserId(current);
		userDaoImpl.save(newUser);

        Optional<User> optionalUser = userDaoImpl.get(current);
        User user2 = optionalUser.orElse(null);
        current++;

        assertEquals(newUser, user2);
    }

    @Test
    public void getUserPositiveTest() {
        Optional<User> optionalUser = userDaoImpl.get(1L);
		User user = optionalUser.orElse(null);

        User user2 = new User();
        user2.setEmail("user1@example.com");
        user2.setUsername("Alex");
        user2.setUsersurname("Ravlex");
        user2.setUserId(1L);

        assertEquals(user, user2);
    }

    @Test
    public void deleteUserPositiveTest() {
        User user = new User();
        user.setEmail("userVas34@example.com");
        user.setUsername("Vasiliy");
        user.setUsersurname("Kasper");
        user.setUserId(current+1); //34
        userDaoImpl.save(user);

        userDaoImpl.delete(user.getUserId());
        Optional<User> deletedUser = userDaoImpl.get(user.getUserId());
        current++;

        assertFalse(deletedUser.isPresent(), "User should be deleted");
    }

    @Test
    public void getAllUsersPositiveTest() {
        User user1 = userDaoImpl.get(1L).orElse(null);
        User user2 = userDaoImpl.get(9L).orElse(null);

        List<User> users = userDaoImpl.getAll();

        assertNotNull(users);
        assertTrue(users.size() >= 2);
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    public void updateUserPositiveTest() {
        User user2 = userDaoImpl.get(7L).orElse(null);
        String email = "KRABSTER777@example.com";
        assert user2 != null;
        user2.setEmail(email);
		userDaoImpl.update(user2);

        User userGet = userDaoImpl.get(7L).orElse(null);
        assert userGet != null;
        assertEquals(userGet.getEmail(), email);
    }

}
