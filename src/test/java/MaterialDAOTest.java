import com.library.dao.MaterialDAO;
import com.library.model.Book;
import com.library.model.Material;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 *
 * @author josef
 */
public class MaterialDAOTest {

    private static MaterialDAO dao;

    @BeforeAll
    public static void setup() {
        dao = new MaterialDAO();
    }

    @Test
    public void testAddMaterial() {
        Material book = new Book(
                0,
                "Libro de Prueba",
                "Autor Prueba",
                10,
                10,
                LocalDate.of(2020, 1, 1)
        );

        dao.addMaterial(book);
        Material mat = dao.getMaterialById(1);

        assertNotNull(mat);
        assertEquals("Libro de Prueba", mat.getTitle());
    }

    @Test
    public void testUpdateAvailability() {
        dao.updateAvailability(1, 9);
        assertEquals(9, dao.getMaterialById(1).getAvailableQuantity());
    }
}
