package com.library.service;

import com.library.controller.LibraryController;
import com.library.model.Loan;
import com.library.model.Material;
import com.library.model.Professor;
import com.library.model.Return;
import com.library.model.Student;
import com.library.model.User;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author josef
 */
public class ConsoleMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryController controller = new LibraryController();

    // ===========================================================
    // ===================== MÉTODOS VISUALES ====================
    // ===========================================================
    /**
     * Limpia la consola de forma compatible con Windows, macOS y Linux.
     */
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la consola");
        }
    }

    /**
     * Imprime un encabezado con formato.
     */
    public static void printHeader(String title, String... infoLines) {
        String line = "=".repeat(150);
        System.out.printf("\n+ %-150s +\n", line);
        System.out.printf("| %-150s |\n", title.toUpperCase());
        System.out.printf("+ %-150s +\n", line);

        for (String info : infoLines) {
            System.out.printf("| %-150s |\n", info);
        }

        System.out.printf("+ %-150s +\n", "-".repeat(150));
    }

    /**
     * Imprime un menú con opciones dinámicas y retorna la opción elegida.
     */
    public static int showMenu(String menuTitle, List<String> options) {
        String line = "=".repeat(150);
        printHeader(menuTitle);

        for (int i = 0; i < options.size(); i++) {
            System.out.printf("| [%d] %-" + (150 - 4) + "s |\n", i + 1, options.get(i));
        }

        System.out.printf("| %-150s |\n", "[0] Salir");
        System.out.printf("+ %-150s +\n", line);
        System.out.print("\nSeleccione una opción: ");

        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Intente de nuevo.");
        }

        return choice;
    }

    /**
     * Imprime una tabla simple con encabezados y filas.
     */
    public static void printTable(String[] headers, List<String[]> rows) {
        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }

        // Calcular ancho máximo por columna
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                widths[i] = Math.max(widths[i], row[i].length());
            }
        }

        printSeparator(widths);
        printRow(headers, widths);
        printSeparator(widths);

        for (String[] row : rows) {
            printRow(row, widths);
        }

        printSeparator(widths);
    }

    private static void printRow(String[] cells, int[] widths) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < cells.length; i++) {
            sb.append(" ").append(String.format("%-" + widths[i] + "s", cells[i])).append(" |");
        }
        System.out.println(sb);
    }

    private static void printSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int width : widths) {
            sb.append("-".repeat(width + 2)).append("+");
        }
        System.out.println(sb);
    }

    // ===========================================================
    // ===================== MENÚ PRINCIPAL ======================
    // ===========================================================
    public static void start() {
        clearConsole();
        printHeader(
                "PROYECTO FINAL - PROGRAMACIÓN II",
                "NOMBRE: José Andrés Flores Barco",
                "CARNE: 0910-24-25339",
                "SECCIÓN: Sábado Virtual"
        );

        List<String> options = List.of(
                "Manejo de usuarios",
                "Manejo de materiales",
                "Préstamos",
                "Devoluciones"
        );

        int choice;
        do {
            choice = showMenu("MENÚ PRINCIPAL", options);

            switch (choice) {
                case 1 ->
                    userMenu();
                case 2 ->
                    materialMenu();
                case 3 ->
                    loanMenu();
                case 4 ->
                    returnMenu();
                case 0 ->
                    System.out.println("Saliendo del sistema...");
                default ->
                    System.out.println("Opción no válida.");
            }

            if (choice != 0) {
                pauseAndClear();
            }
        } while (choice != 0);
    }

    // ===========================================================
    // ====================== SUBMENÚS ============================
    // ===========================================================
    private static void userMenu() {
        List<String> options = List.of(
                "Registrar usuario (Tipo Estudiante)",
                "Registrar usuario (Tipo Profesor)",
                "Listar usuarios"
        );

        int choice;
        do {
            choice = showMenu("GESTIÓN DE USUARIOS", options);

            switch (choice) {
                case 1 ->
                    registerStudent();
                case 2 ->
                    registerProfessor();
                case 3 -> {
                    List<User> users = controller.listUsers();

                    if (users.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        List<String[]> rows = new java.util.ArrayList<>();

                        for (User user : users) {
                            rows.add(new String[]{
                                String.valueOf(user.getUserId()),
                                user.getName(),
                                user.getEmail(),
                                user.getUserType()
                            });
                        }

                        printTable(
                                new String[]{"ID", "NOMBRE", "EMAIL", "TIPO DE USUARIO"},
                                rows
                        );
                    }
                }
                case 0 ->
                    System.out.println("Volviendo al menú principal...");
                default ->
                    System.out.println("Opción no válida.");
            }

            if (choice != 0) {
                pauseAndClear();
            }
        } while (choice != 0);
    }

    private static void materialMenu() {
        List<String> options = List.of(
                "Registrar material",
                "Listar materiales",
                "Eliminar material"
        );

        int choice;
        do {
            choice = showMenu("GESTIÓN DE MATERIALES", options);

            switch (choice) {
                case 1 ->
                    registerMaterial();
                case 2 -> {
                    List<Material> materials = controller.listMaterials();

                    if (materials.isEmpty()) {
                        System.out.println("No hay Materiales (Libros, Revistas y Thesis) registrados.");
                    } else {
                        List<String[]> rows = new java.util.ArrayList<>();

                        for (Material material : materials) {
                            rows.add(new String[]{
                                String.valueOf(material.getMaterialId()),
                                material.getTitle(),
                                material.getAuthor(),
                                material.getMaterialType(),
                                String.valueOf(material.getTotalQuantity()),
                                String.valueOf(material.getAvailableQuantity()),
                                String.valueOf(material.getPublicationDate())
                            });
                        }

                        printTable(
                                new String[]{"ID", "TITULO", "AUTOR", "TIPO DE MATERIAL", "TOTAL", "DISPONIBLE", "FECHA DE PUBLICACION"},
                                rows
                        );
                    }
                }
                case 3 ->
                    deleteMaterial();
                case 0 ->
                    System.out.println("Volviendo al menú principal...");
                default ->
                    System.out.println("Opción no válida.");
            }

            if (choice != 0) {
                pauseAndClear();
            }
        } while (choice != 0);
    }

    private static void loanMenu() {
        List<String> options = List.of(
                "Registrar préstamo",
                "Listar préstamos"
        );

        int choice;
        do {
            choice = showMenu("GESTIÓN DE PRÉSTAMOS", options);

            switch (choice) {
                case 1 ->
                    registerLoan();
                case 2 -> {
                    List<Loan> loans = controller.listLoans();

                    if (loans.isEmpty()) {
                        System.out.println("No hay Prestamos registrados.");
                    } else {
                        List<String[]> rows = new java.util.ArrayList<>();

                        for (Loan loan : loans) {
                            rows.add(new String[]{
                                String.valueOf(loan.getLoanId()),
                                String.valueOf(loan.getUserId()),
                                String.valueOf(loan.getMaterialId()),
                                String.valueOf(loan.getLoanDate()),
                                String.valueOf(loan.getDueDate()),
                                String.valueOf(loan.getReturnDate()),
                                String.valueOf(loan.getFine())
                            });
                        }

                        printTable(
                                new String[]{"ID", "ID USUARIO", "ID MATERIAL ", "FECHA PRESTAMO", "FECHA VENCIMIENTO", "FECHA RETORNO", "MULTA"},
                                rows
                        );
                    }
                }
                case 0 ->
                    System.out.println("Volviendo al menú principal...");
                default ->
                    System.out.println("Opción no válida.");
            }

            if (choice != 0) {
                pauseAndClear();
            }
        } while (choice != 0);
    }

    private static void returnMenu() {
        List<String> options = List.of(
                "Registrar devolución",
                "Listar devoluciones"
        );

        int choice;
        do {
            choice = showMenu("GESTIÓN DE DEVOLUCIONES", options);

            switch (choice) {
                case 1 ->
                    registerReturn();
                case 2 -> {
                    List<Return> returns = controller.listReturns();

                    if (returns.isEmpty()) {
                        System.out.println("No hay Retornos registrados.");
                    } else {
                        List<String[]> rows = new java.util.ArrayList<>();

                        for (Return return1 : returns) {
                            rows.add(new String[]{
                                String.valueOf(return1.getReturnId()),
                                String.valueOf(return1.getLoanId()),
                                String.valueOf(return1.getReturnDate()),
                                String.valueOf(return1.getFine()),});
                        }

                        printTable(
                                new String[]{"ID", "ID PRESTAMO", "FECHA DE RETORNO ", "MULTA"},
                                rows
                        );
                    }
                }
                case 0 ->
                    System.out.println("Volviendo al menú principal...");
                default ->
                    System.out.println("Opción no válida.");
            }

            if (choice != 0) {
                pauseAndClear();
            }
        } while (choice != 0);
    }

    // ===========================================================
    // ================= FUNCIONALIDAD BÁSICA ====================
    // ===========================================================
    private static void registerStudent() {
        System.out.print("Ingrese nombre del usuario: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese correo del usuario: ");
        String email = scanner.nextLine();

        Student student = new Student(name, email);
        controller.registerUser(student);

        System.out.println("✅ Usuario tipo estudiante registrado con éxito.");
    }

    private static void registerProfessor() {
        System.out.print("Ingrese nombre del usuario: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese correo del usuario: ");
        String email = scanner.nextLine();

        Professor professor = new Professor(name, email);
        controller.registerUser(professor);

        System.out.println("✅ Usuario tipo profesor registrado con éxito.");
    }

    private static void registerMaterial() {
        System.out.print("Ingrese título del material: ");
        String title = scanner.nextLine();
        System.out.print("Ingrese tipo de material: ");
        String type = scanner.nextLine();

        //controller.registerMaterial(title, type);
        System.out.println("✅ Material registrado correctamente.");
    }

    private static void deleteMaterial() {
        System.out.print("Ingrese ID del material a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        //controller.deleteMaterial(id);
    }

    private static void registerLoan() {
        System.out.print("Ingrese ID del usuario: ");
        int userId = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese ID del material: ");
        int materialId = Integer.parseInt(scanner.nextLine());

        controller.makeLoan(userId, materialId);
        System.out.println("✅ Préstamo registrado correctamente.");
    }

    private static void registerReturn() {
        System.out.print("Ingrese ID del préstamo: ");
        int loanId = Integer.parseInt(scanner.nextLine());
        controller.returnMaterial(loanId);
        System.out.println("✅ Devolución registrada correctamente.");
    }

    private static void pauseAndClear() {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
        clearConsole();
    }
}
