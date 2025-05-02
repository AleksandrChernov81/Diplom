package ru.netology.data;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    // Конфигурация по умолчанию для локальной разработки
    private static final String DEFAULT_JDBC_URL = "jdbc:mysql://localhost:3307/app?" +
            "useSSL=false&" +
            "allowPublicKeyRetrieval=true&" +
            "serverTimezone=UTC";
    private static final String DEFAULT_DB_USER = "app";
    private static final String DEFAULT_DB_PASSWORD = "pass";

    @SneakyThrows
    private static Connection getConnection() {
        final String url = getConfigValue("DB_URL", DEFAULT_JDBC_URL);
        final String user = getConfigValue("DB_USER", DEFAULT_DB_USER);
        final String password = getConfigValue("DB_PASSWORD", DEFAULT_DB_PASSWORD);

        validateConnectionParams(url, user, password);
        log.debug("Connecting to database: {}", url);

        return DriverManager.getConnection(url, user, password);
    }

    private static String getConfigValue(String envVar, String defaultValue) {
        String value = System.getenv(envVar);
        return Objects.requireNonNullElse(value, defaultValue);
    }

    private static void validateConnectionParams(String url, String user, String password) {
        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Database URL is not configured!");
        }
        if (user == null || user.isBlank()) {
            throw new IllegalStateException("Database user is not configured!");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("Database password is not configured!");
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
            return runner.query(conn, sql, new ScalarHandler<>());
        }
    }

    public static void assertPaymentStatus(String expectedStatus) {
        String actualStatus = getPaymentStatus();
        assertEquals(expectedStatus, actualStatus,
                "Payment status mismatch. Expected: " + expectedStatus + ", Actual: " + actualStatus);
    }

    @SneakyThrows
    public static String getCreditStatus() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
            return runner.query(conn, sql, new ScalarHandler<>());
        }
    }

    public static void assertCreditStatus(String expectedStatus) {
        String actualStatus = getCreditStatus();
        assertEquals(expectedStatus, actualStatus,
                "Credit status mismatch. Expected: " + expectedStatus + ", Actual: " + actualStatus);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (Connection conn = getConnection()) {
            runner.execute(conn, "DELETE FROM credit_request_entity");
            runner.execute(conn, "DELETE FROM payment_entity");
            runner.execute(conn, "DELETE FROM order_entity");
            log.info("Database cleaned successfully");
        }
    }

    public static void expectedCreditStatus(String approved) {
    }

    public static void expectedPaymentStatus(String approved) {
    }
}