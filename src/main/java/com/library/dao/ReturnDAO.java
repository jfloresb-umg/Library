package com.library.dao;

import com.library.model.Return;
import java.sql.Connection;
import java.sql.Date;
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
public class ReturnDAO {

    public void addReturn(Return ret) {
        String sql = "INSERT INTO Returns (loan_id, return_date, fine) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ret.getLoanId());
            stmt.setDate(2, Date.valueOf(ret.getReturnDate()));
            stmt.setDouble(3, ret.getFine());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar devolución: " + e.getMessage());
        }
    }

    public List<Return> getAllReturns() {
        List<Return> returns = new ArrayList<>();
        String sql = "SELECT * FROM Returns";

        try (Connection conn = ConnectionDB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Return ret = new Return(
                        rs.getInt("return_id"),
                        rs.getInt("loan_id"),
                        rs.getDate("return_date").toLocalDate(),
                        rs.getDouble("fine")
                );
                returns.add(ret);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener devoluciones: " + e.getMessage());
        }

        return returns;
    }
}
