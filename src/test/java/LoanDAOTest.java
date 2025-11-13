import com.library.dao.LoanDAO;
import com.library.model.Loan;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 *
 * @author josef
 */
public class LoanDAOTest {

    private static LoanDAO loanDAO;

    @BeforeAll
    static void setup() {
        loanDAO = new LoanDAO();
    }

    @Test
    public void testAddLoan() {
        Loan loan = new Loan(1, 1, LocalDate.now().plusDays(7));
        loanDAO.addLoan(loan);

        Loan result = loanDAO.getLoanById(1);
        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }

    @Test
    public void testUpdateReturn() {
        loanDAO.updateReturn(1, 5.0);
        Loan result = loanDAO.getLoanById(1);
        assertEquals(5.0, result.getFine());
        assertNotNull(result.getReturnDate());
    }
}