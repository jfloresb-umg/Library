package com.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class ConnectionDB {
    private static Connection connection;

    // Cargar configuración desde db.properties
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try (InputStream input = ConnectionDB.class.getClassLoader()
                    .getResourceAsStream("db.properties")) {

                Properties props = new Properties();
                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Conexión establecida con SQL Server.");
            } catch (Exception e) {
                System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
                throw new SQLException(e);
            }
        }
        return connection;
    }
}
