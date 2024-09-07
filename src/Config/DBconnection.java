package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    public static Connection conn = null;


    public static void DBconnection () {
        String URL = "jdbc:postgresql://localhost:5432/EcoMove";
        String USERNAME = "postgres";
        String PASSWORD = "root";

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static  Connection getConnection() {
        if (conn == null) {
           DBconnection();
            return conn;
        }else {
            return conn;
        }
    }


}
