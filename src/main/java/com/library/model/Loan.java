package com.library.model;

import java.time.LocalDate;

/**
 *
 * @author josef
 */
public class Loan {

    private int loanId;
    private int userId;
    private int materialId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;

    public Loan() {
    }

    public Loan(int loanId, int userId, int materialId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, double fine) {
        this.loanId = loanId;
        this.userId = userId;
        this.materialId = materialId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }

    // Constructor para nuevos préstamos (sin ID todavía)
    public Loan(int userId, int materialId, LocalDate dueDate) {
        this.userId = userId;
        this.materialId = materialId;
        this.loanDate = LocalDate.now();
        this.dueDate = dueDate;
        this.fine = 0.0;
    }

    // Getters y Setters
    public int getLoanId() {
        return loanId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "Loan #" + loanId
                + " | User: " + userId
                + " | Material: " + materialId
                + " | Due: " + dueDate
                + (returnDate != null ? " | Returned: " + returnDate : "");
    }
}
