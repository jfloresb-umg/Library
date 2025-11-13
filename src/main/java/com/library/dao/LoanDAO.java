package com.library.dao;

import com.library.model.Loan;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josef
 */
public class LoanDAO {

    public void addLoan(Loan loan) {
        String sql = "INSERT INTO Loans (user_id, material_id, loan_date, due_date, fine) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loan.getUserId());
            stmt.setInt(2, loan.getMaterialId());
            stmt.setDate(3, Date.valueOf(loan.getLoanDate()));
            stmt.setDate(4, Date.valueOf(loan.getDueDate()));
            stmt.setDouble(5, loan.getFine());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar préstamo: " + e.getMessage());
        }
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans";

        try (Connection conn = ConnectionDB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Loan loan = new Loan(
                        rs.getInt("loan_id"),
                        rs.getInt("user_id"),
                        rs.getInt("material_id"),
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
                        rs.getDouble("fine")
                );
                loans.add(loan);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener préstamos: " + e.getMessage());
        }

        return loans;
    }

    public Loan getLoanById(int loanId) {
        Loan loan = null;
        String query = "SELECT loan_id, user_id, material_id, loan_date, due_date, return_date, fine "
                + "FROM Loans WHERE loan_id = ?";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, loanId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate loanDate = rs.getDate("loan_date").toLocalDate();
                    LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                    LocalDate returnDate = null;

                    Date sqlReturn = rs.getDate("return_date");
                    if (sqlReturn != null) {
                        returnDate = sqlReturn.toLocalDate();
                    }

                    loan = new Loan(
                            rs.getInt("loan_id"),
                            rs.getInt("user_id"),
                            rs.getInt("material_id"),
                            loanDate,
                            dueDate,
                            returnDate,
                            rs.getDouble("fine")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener préstamo con ID " + loanId + ": " + e.getMessage());
        }

        return loan;
    }

    public void updateReturn(int loanId, double fine) {
        String query = "UPDATE Loans SET return_date = ?, fine = ? WHERE loan_id = ?";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDouble(2, fine);
            stmt.setInt(3, loanId);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Préstamo actualizado correctamente. ID: " + loanId);
            } else {
                System.out.println("⚠️ No se encontró el préstamo con ID: " + loanId);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar devolución del préstamo: " + e.getMessage());
        }
    }

}
