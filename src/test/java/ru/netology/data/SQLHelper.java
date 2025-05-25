// SQLHelper.java
package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    private static final String url = System.getProperty("dbUrl");
    private static final String username = System.getProperty("dbUsername");
    private static final String password = System.getProperty("dbPassword");

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        try (var connection = getConn()) {
            var SQLQuery = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
            return runner.query(connection, SQLQuery, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getCreditStatus() {
        try (var connection = getConn()) {
            var SQLQuery = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
            return runner.query(connection, SQLQuery, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var connection = getConn()) {
            runner.execute(connection, "DELETE FROM credit_request_entity");
            runner.execute(connection, "DELETE FROM payment_entity");
            runner.execute(connection, "DELETE FROM order_entity");
        }
    }

    public static void verifyCreditStatus(String expectedStatus) {
        String actualStatus = getCreditStatus();
        assertNotNull(actualStatus, "Статус кредитной заявки не найден");
        assertEquals(expectedStatus, actualStatus, "Неверный статус кредитной заявки");
    }

    public static void verifyPaymentStatus(String expectedStatus) {
        String actualStatus = getPaymentStatus();
        assertNotNull(actualStatus, "Статус платежа не найден");
        assertEquals(expectedStatus, actualStatus, "Неверный статус платежа");
    }
}