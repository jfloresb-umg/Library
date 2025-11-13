package com.library.controller;

import com.library.dao.LoanDAO;
import com.library.dao.MaterialDAO;
import com.library.dao.ReturnDAO;
import com.library.dao.UserDAO;
import com.library.model.Loan;
import com.library.model.Material;
import com.library.model.Return;
import com.library.model.User;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author josef
 */
public class LibraryController {

    private final UserDAO userDAO;
    private final MaterialDAO materialDAO;
    private final LoanDAO loanDAO;
    private final ReturnDAO returnDAO;

    public LibraryController() {
        this.userDAO = new UserDAO();
        this.materialDAO = new MaterialDAO();
        this.loanDAO = new LoanDAO();
        this.returnDAO = new ReturnDAO();
    }

    // ================================
    //         USUARIOS
    // ================================
    public void registerUser(User user) {
        try {
            userDAO.addUser(user);
            System.out.println("✅ Usuario registrado: " + user.getName());
        } catch (Exception e) {
            System.err.println("❌ Error al registrar usuario: " + e.getMessage());
        }
    }

    public List<User> listUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            System.err.println("❌ Error al obtener usuarios: " + e.getMessage());
            return null;
        }
    }

    // ================================
    //         MATERIALES
    // ================================
    public void registerMaterial(Material material) {
        try {
            materialDAO.addMaterial(material);
            System.out.println("✅ Material registrado: " + material.getTitle());
        } catch (Exception e) {
            System.err.println("❌ Error al registrar material: " + e.getMessage());
        }
    }

    public List<Material> listMaterials() {
        try {
            return materialDAO.getAllMaterials();
        } catch (Exception e) {
            System.err.println("❌ Error al obtener materiales: " + e.getMessage());
            return null;
        }
    }

    // ================================
    //         PRÉSTAMOS
    // ================================
    public void makeLoan(int userId, int materialId) {
        try {
            User user = userDAO.getUserById(userId);
            Material material = materialDAO.getMaterialById(materialId);

            if (user == null || material == null) {
                System.err.println("❌ Usuario o material no encontrado.");
                return;
            }

            if (material.getAvailableQuantity() <= 0) {
                System.err.println("❌ No hay ejemplares disponibles.");
                return;
            }

            int days = material.getMaxLoanDays();
            LocalDate dueDate = LocalDate.now().plusDays(days);

            Loan loan = new Loan(userId, materialId, dueDate);
            loanDAO.addLoan(loan);

            // Actualiza cantidad disponible
            materialDAO.updateAvailability(materialId, material.getAvailableQuantity() - 1);

            System.out.println("✅ Préstamo registrado correctamente. Entregar antes del: " + dueDate);

        } catch (Exception e) {
            System.err.println("❌ Error al registrar préstamo: " + e.getMessage());
        }
    }

    public List<Loan> listLoans() {
        try {
            return loanDAO.getAllLoans();
        } catch (Exception e) {
            System.err.println("❌ Error al obtener préstamos: " + e.getMessage());
            return null;
        }
    }

    // ================================
    //         DEVOLUCIONES
    // ================================
    public void returnMaterial(int loanId) {
        try {
            Loan loan = loanDAO.getLoanById(loanId);
            if (loan == null) {
                System.err.println("❌ No se encontró el préstamo con ID " + loanId);
                return;
            }

            LocalDate today = LocalDate.now();
            double fine = 0;

            // Calcular multa si hay retraso
            if (today.isAfter(loan.getDueDate())) {
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(loan.getDueDate(), today);
                fine = daysLate * 2.0; // Q2 por día de retraso
                System.out.println("⚠️ Devolución con retraso: " + daysLate + " días. Multa total: Q" + fine);
            }

            // 1️⃣ Actualizar registro en Loans
            loanDAO.updateReturn(loanId, fine);

            // 2️⃣ Insertar registro en Returns
            Return ret = new Return(loanId, today, fine);
            returnDAO.addReturn(ret);

            // 3️⃣ Actualizar cantidad disponible del material
            Material material = materialDAO.getMaterialById(loan.getMaterialId());
            if (material != null) {
                materialDAO.updateAvailability(material.getMaterialId(), material.getAvailableQuantity() + 1);
            }

            System.out.println("✅ Devolución registrada correctamente.");

        } catch (Exception e) {
            System.err.println("❌ Error al procesar devolución: " + e.getMessage());
        }
    }

    public List<Return> listReturns() {
        try {
            return returnDAO.getAllReturns();
        } catch (Exception e) {
            System.err.println("❌ Error al obtener devoluciones: " + e.getMessage());
            return null;
        }
    }
}
