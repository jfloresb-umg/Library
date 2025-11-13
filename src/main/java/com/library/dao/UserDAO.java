package com.library.dao;

import com.library.model.Professor;
import com.library.model.Student;
import com.library.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josef
 */
public class UserDAO {

    public void addUser(User user) {
        String sql = "INSERT INTO Users (name, email, user_type) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getUserType());
            stmt.executeUpdate();

            System.out.println("✅ Usuario registrado: " + user.getName());
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar usuario: " + e.getMessage());
        }
    }

    public void addStudent(User user) {
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = ConnectionDB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String type = rs.getString("user_type");

                User user;
                if ("student".equalsIgnoreCase(type)) {
                    user = new Student(id, name, email);
                } else {
                    user = new Professor(id, name, email);
                }
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener usuarios: " + e.getMessage());
        }

        return users;
    }

    public User getUserById(int userId) {
        User user = null;
        String query = "SELECT user_id, name, email, user_type FROM Users WHERE user_id = ?";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("user_type");

                    // Crear la subclase adecuada
                    switch (type.toLowerCase()) {
                        case "student" -> {
                            user = new Student(
                                    rs.getInt("user_id"),
                                    rs.getString("name"),
                                    rs.getString("email")
                            );
                        }
                        case "professor" -> {
                            user = new Professor(
                                    rs.getInt("user_id"),
                                    rs.getString("name"),
                                    rs.getString("email")
                            );
                        }
                        default -> {
                            System.err.println("⚠️ Tipo de usuario desconocido: " + type);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener el usuario con ID " + userId + ": " + e.getMessage());
        }

        return user;
    }

}
