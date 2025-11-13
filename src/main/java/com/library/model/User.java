package com.library.model;

/**
 *
 * @author josef
 */
public abstract class User {

    protected int userId;
    protected String name;
    protected String email;
    protected String userType;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String userType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public User(int userId, String name, String email, String userType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // Método abstracto: cada tipo de usuario tiene límites distintos
    public abstract int getMaxLoanDays();

    public abstract double calculateFine(double baseFine, int overdueDays);

    @Override
    public String toString() {
        return "[" + userType.toUpperCase() + "] " + name + " (" + email + ")";
    }
}
