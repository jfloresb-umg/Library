package com.library.dao;

import com.library.model.Book;
import com.library.model.Journal;
import com.library.model.Material;
import com.library.model.Thesis;
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
public class MaterialDAO {

    public void addMaterial(Material material) {
        String sql = "INSERT INTO Materials (title, author, material_type, total_quantity, available_quantity, publication_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, material.getTitle());
            stmt.setString(2, material.getAuthor());
            stmt.setString(3, material.getMaterialType());
            stmt.setInt(4, material.getTotalQuantity());
            stmt.setInt(5, material.getAvailableQuantity());
            stmt.setDate(6, Date.valueOf(material.getPublicationDate()));

            stmt.executeUpdate();
            System.out.println("✅ Material agregado: " + material.getTitle());

        } catch (SQLException e) {
            System.err.println("❌ Error al agregar material: " + e.getMessage());
        }
    }

    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials";

        try (Connection conn = ConnectionDB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("material_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String type = rs.getString("material_type");
                int total = rs.getInt("total_quantity");
                int available = rs.getInt("available_quantity");
                LocalDate date = rs.getDate("publication_date").toLocalDate();

                Material material;
                switch (type.toLowerCase()) {
                    case "book":
                        material = new Book(id, title, author, total, available, date);
                        break;
                    case "journal":
                        material = new Journal(id, title, author, total, available, date);
                        break;
                    default:
                        material = new Thesis(id, title, author, total, available, date);
                        break;
                }
                materials.add(material);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener materiales: " + e.getMessage());
        }

        return materials;
    }

    public void updateAvailability(int materialId, int newQuantity) {
        String sql = "UPDATE Materials SET available_quantity = ? WHERE material_id = ?";
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, materialId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar disponibilidad: " + e.getMessage());
        }
    }

    public Material getMaterialById(int materialId) {
        Material material = null;
        String query = "SELECT material_id, title, author, material_type, total_quantity, available_quantity, publication_date "
                + "FROM Materials WHERE material_id = ?";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, materialId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("material_type").toLowerCase();
                    LocalDate publicationDate = null;

                    Date sqlDate = rs.getDate("publication_date");
                    if (sqlDate != null) {
                        publicationDate = sqlDate.toLocalDate();
                    }

                    // Crear la subclase adecuada según el tipo
                    switch (type) {
                        case "book" -> {
                            material = new Book(
                                    rs.getInt("material_id"),
                                    rs.getString("title"),
                                    rs.getString("author"),
                                    rs.getInt("total_quantity"),
                                    rs.getInt("available_quantity"),
                                    publicationDate
                            );
                        }
                        case "journal" -> {
                            material = new Journal(
                                    rs.getInt("material_id"),
                                    rs.getString("title"),
                                    rs.getString("author"),
                                    rs.getInt("total_quantity"),
                                    rs.getInt("available_quantity"),
                                    publicationDate
                            );
                        }
                        case "thesis" -> {
                            material = new Thesis(
                                    rs.getInt("material_id"),
                                    rs.getString("title"),
                                    rs.getString("author"),
                                    rs.getInt("total_quantity"),
                                    rs.getInt("available_quantity"),
                                    publicationDate
                            );
                        }
                        default -> {
                            System.err.println("⚠️ Tipo de material desconocido: " + type);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener material con ID " + materialId + ": " + e.getMessage());
        }

        return material;
    }

}
