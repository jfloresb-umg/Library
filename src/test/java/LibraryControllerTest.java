import com.library.controller.LibraryController;
import com.library.model.Loan;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author josef
 */
public class LibraryControllerTest {

    private static LibraryController controller;

    @BeforeAll
    static void init() {
        controller = new LibraryController();
    }

    @Test
    public void testLoanAndReturn() {
        controller.makeLoan(1, 1);

        Loan loan = controller.listLoans().get(0);
        assertNotNull(loan);

        controller.returnMaterial(loan.getLoanId());

        assertNotNull(loan.getReturnDate());
    }
}