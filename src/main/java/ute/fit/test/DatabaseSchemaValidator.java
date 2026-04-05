package ute.fit.test;

import jakarta.persistence.EntityManager;
import ute.fit.config.JPAUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class DatabaseSchemaValidator {
    public static void main(String[] args) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            Connection conn = em.unwrap(Connection.class);
            DatabaseMetaData metaData = conn.getMetaData();

            // Liệt kê tất cả tables
            System.out.println("=== DATABASE TABLES ===");
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                // Liệt kê columns cho mỗi table
                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                System.out.println("  Columns:");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = columns.getInt("COLUMN_SIZE");
                    System.out.println("    - " + columnName + " (" + columnType + "(" + columnSize + "))");
                }
                columns.close();
                System.out.println();
            }
            tables.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JPAUtil.shutdown();
        }
    }
}