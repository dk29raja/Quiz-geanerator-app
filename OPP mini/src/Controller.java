//package sample;
//No change needed.
import java.sql.*;
import java.lang.*;
import java.sql.DriverManager;


import static java.sql.DriverManager.getConnection;

public class Controller {

    public static Connection conn;

    public static Connection getconn(){

        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String username = "bill";
            String password = "Swift@13";
            conn = getConnection("jdbc:mysql://localhost:3306/mysql",username,password);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection error");
        }

        return conn;
    }

}



