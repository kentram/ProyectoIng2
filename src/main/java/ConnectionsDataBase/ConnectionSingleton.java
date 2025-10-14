package ConnectionsDataBase;

import java.sql.*;

public class ConnectionSingleton {

    private static ConnectionSingleton INSTANCE = null; //
    private Connection conn;
    private static final String USER = "postgres";
    private static final String PASSWORD = "0278";
    private static final String DB_NAME = "negocio_db"; // Base de datos fija
    private ConnectionSingleton() {
        try {
            String url = "jdbc:postgresql://localhost:5432/" + DB_NAME;
            conn = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base: " + DB_NAME);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base: " + DB_NAME + " - " + e.getMessage());
            conn = null;
        }
    }

    private static void createInstance() {
        INSTANCE = new ConnectionSingleton();
    }

    public static ConnectionSingleton getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        return conn;
    }

    public boolean existDataBase(String nombreBD) {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM pg_database WHERE datname = ?")) {
            ps.setString(1, nombreBD);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error al verificar existencia de base de datos: " + e.getMessage());
            return false;
        }
    }

    public void createDataBase(String nombreBD) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE \"" + nombreBD + "\"");
            System.out.println("Base de datos '" + nombreBD + "' creada.");
        } catch (SQLException e) {
            System.out.println("Error al crear base de datos: " + e.getMessage());
        }
    }
}
