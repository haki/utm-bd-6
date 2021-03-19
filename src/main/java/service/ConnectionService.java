package service;

import java.sql.*;

public class ConnectionService {
    private static String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String DB_URL = "jdbc:sqlserver://localhost\\MSSQLSERVER;database=universitatea";

    private static Connection conn = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static DatabaseMetaData databaseMetaData = null;

    public static boolean connection(String username, String password) {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to a selected database...");

            conn = DriverManager.getConnection(DB_URL, username, password);
            System.out.println("Connected database successfully.");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean checkTable(String tableName) throws SQLException {
        databaseMetaData = getConn().getMetaData();
        resultSet = databaseMetaData.getTables(null, null, tableName, null);

        if (resultSet.next()) {
            resultSet.close();
            return true;
        }

        return false;
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        ConnectionService.conn = conn;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        ConnectionService.statement = statement;
    }
}
