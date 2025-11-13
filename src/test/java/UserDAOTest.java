
import com.library.dao.UserDAO;
import com.library.model.Student;
import com.library.model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author josef
 */


public class UserDAOTest {

    private static UserDAO userDAO;

    @BeforeAll
    public static void setup() {
        userDAO = new UserDAO();
    }

    @Test
    public void testAddUser() {
        User user = new Student(0, "Test User", "test@example.com");
        userDAO.addUser(user);
        assertNotNull(userDAO.getUserById(1));
    }

    @Test
    public void testGetUserById() {
        User user = userDAO.getUserById(1);
        assertNotNull(user);
        assertEquals("Test User", user.getName());
    }

    @Test
    public void testGetAllUsers() {
        assertFalse(userDAO.getAllUsers().isEmpty());
    }
}
