package DataBase;

import java.sql.*;

public class GetDataBaseConnection {
    static final String url = "jdbc:mysql://127.0.0.1:3306/airports";
    static final String user = "root";
    static final String password = "*****";
    static public Connection get_database_connection() {
        try {
            return DriverManager.getConnection(url, user, password);

        } catch(SQLException e) {
            System.out.println("Exception occurred at getConnection [GetDataBaseConnection] Class\nError: " + e.getMessage());
        }
        return null;
    }
}

