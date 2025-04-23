package ru.netology.data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLHelper {

    private static QueryRunner runner = new QueryRunner();

    private static String url = System.getProperty("dbUrl");
    private static String username = System.getProperty("dbUsername");
    private static String password = System.getProperty("dbPassword");

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var connection = getConn();
        var SQLQuery = "SELECT status FROM payment_entity ORDER BY created DESC";
        return runner.query(connection, SQLQuery, new ScalarHandler<String>());
    }


    public static void expectedPaymentStatus(String status) {
        assertEquals(status, getPaymentStatus());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var connection = getConn();
        var SQLQuery = "SELECT status FROM credit_request_entity ORDER BY created DESC";
        return runner.query(connection, SQLQuery, new ScalarHandler<String>());
    }


    public static void expectedCreditStatus(String status) {
        assertEquals(status, getCreditStatus());
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM order_entity");
    }
}
