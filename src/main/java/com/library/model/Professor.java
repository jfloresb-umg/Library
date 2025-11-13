package com.library.model;

/**
 *
 * @author josef
 */
public class Professor extends User {

    public Professor(String name, String email) {
        super(name, email, "professor");
    }

    public Professor(int userId, String name, String email) {
        super(userId, name, email, "professor");
    }

    @Override
    public int getMaxLoanDays() {
        return 14; // Máximo 14 días de préstamo
    }

    @Override
    public double calculateFine(double baseFine, int overdueDays) {
        return (baseFine * overdueDays) * 0.5; // 50% menos multa
    }
}
