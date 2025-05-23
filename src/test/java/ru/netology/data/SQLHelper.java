package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    private static String url;
    private static String username;
    private static String password;

    static {
        String dbType = System.getProperty("dbType", "postgres").toLowerCase();
        switch (dbType) {
            case "mysql":
                url = System.getProperty("dbUrl", "jdbc:mysql://localhost:3307/app");
                username = System.getProperty("dbUsername", "root");
                password = System.getProperty("dbPassword", "");
                break;
            case "postgres":
                url = System.getProperty("dbUrl", "jdbc:postgresql://localhost:5433/app");
                username = System.getProperty("dbUsername", "app");
                password = System.getProperty("dbPassword", "pass");
                break;
            default:
                throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
    }

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        try (Connection connection = getConn()) {
            String query = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
            return runner.query(connection, query, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getCreditStatus() {
        try (Connection connection = getConn()) {
            String query = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
            return runner.query(connection, query, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void clean() {
        try (Connection connection = getConn()) {
            runner.execute(connection, "DELETE FROM credit_request_entity");
            runner.execute(connection, "DELETE FROM payment_entity");
            runner.execute(connection, "DELETE FROM order_entity");
        }
    }

    public static void verifyCreditStatus(String expectedStatus) {
        String actualStatus = getCreditStatus();
        assertEquals(expectedStatus, actualStatus, "Credit status mismatch");
    }

    public static void verifyPaymentStatus(String expectedStatus) {
        String actualStatus = getPaymentStatus();
        assertEquals(expectedStatus, actualStatus, "Payment status mismatch");
    }
}