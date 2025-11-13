package com.library.model;

/**
 *
 * @author josef
 */
public class Student extends User {

    public Student() {
    }

    public Student(String name, String email) {
        super(name, email, "student");
    }

    public Student(int userId, String name, String email) {
        super(userId, name, email, "student");
    }

    @Override
    public int getMaxLoanDays() {
        return 7; // Máximo 7 días de préstamo
    }

    @Override
    public double calculateFine(double baseFine, int overdueDays) {
        return baseFine * overdueDays; // Multa base por cada día de retraso
    }
}
