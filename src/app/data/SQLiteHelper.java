package app.data;

import java.sql.*;

public class SQLiteHelper {

    private static Connection connection;
    private static boolean hasData = false;

    public static Connection getConnection() {
        try {
            if (connection == null) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ToDoNoteApp.db");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initialize() throws SQLException {
        if (!hasData) {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = 'notes'");

            if (!resultSet.next()) {
                System.out.println("Building the tables.");
                Statement statementCreate = getConnection().createStatement();
                statementCreate.execute("CREATE TABLE notes" +
                        "(id integer, " +
                        "title varchar(60)," +
                        "description text," +
                        "done numeric(1)," +
                        "primary key (id));");
            }

            hasData = true;
        }
    }

}
