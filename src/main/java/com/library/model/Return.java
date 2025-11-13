package com.library.model;

import java.time.LocalDate;

/**
 *
 * @author josef
 */
public class Return {

    private int returnId;
    private int loanId;
    private LocalDate returnDate;
    private double fine;

    public Return(int loanId, LocalDate returnDate, double fine) {
        this.loanId = loanId;
        this.returnDate = returnDate;
        this.fine = fine;
    }

    public Return(int returnId, int loanId, LocalDate returnDate, double fine) {
        this.returnId = returnId;
        this.loanId = loanId;
        this.returnDate = returnDate;
        this.fine = fine;
    }

    // Constructor sin ID (para nueva devolución)
    public Return(int loanId, double fine) {
        this.loanId = loanId;
        this.returnDate = LocalDate.now();
        this.fine = fine;
    }

    // Getters
    public int getReturnId() {
        return returnId;
    }

    public int getLoanId() {
        return loanId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double getFine() {
        return fine;
    }

    @Override
    public String toString() {
        return "Return #" + returnId
                + " | Loan: " + loanId
                + " | Date: " + returnDate
                + " | Fine: Q" + fine;
    }
}
