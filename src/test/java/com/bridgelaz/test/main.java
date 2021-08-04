package com.bridgelaz.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.*;

public class main extends Base {
    public int count;
    private static Connection connections;
    static Connection connection = null;
    private static Statement statement;
    public static String DB_URL = "jdbc:mysql://localhost/spotify";

    @BeforeTest
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connections = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connections;
    }

    @Test(priority = 0)
    public void get_table_data() throws ClassNotFoundException, SQLException {

        Connection connections = this.getConnection();
        Statement statement = connections.createStatement();
        String query = "select * from User";
        System.out.println("/********** Show the table**************/");
        ResultSet res = statement.executeQuery(query);

        while (res.next()) {
            int user_id = res.getInt(1);
            String user_name = res.getString(2);
            String user_email = res.getString(3);
            String user_password = res.getString(4);
            System.out.println(user_id + " " + user_name + "" + user_email + "" + user_password);
            count++;
        }

    }

    @Test(priority = 1)
    public void insert_table_data() throws ClassNotFoundException, SQLException {
        connections = this.getConnection();
        PreparedStatement  preparedstatement = connections.prepareStatement("insert into User values(?,?,?,?)");
        preparedstatement.setInt(1, 200);
        preparedstatement.setString(2, "ramu");
        preparedstatement.setString(3, "ramu23@gmail.com");
        preparedstatement.setString(4, "ramu@589");
        preparedstatement.executeUpdate();
        get_table_data();
    }


    @Test(priority = 2)
    public void update_table_data() throws ClassNotFoundException, SQLException {
        connections = this.getConnection();
        PreparedStatement preparedstatement = connections.prepareStatement("update User set user_name = ? where user_id = ?");
        preparedstatement.setString(1, "Ramesh");
        preparedstatement.setInt(2, 200);
        preparedstatement.executeUpdate();
        get_table_data();
    }

    @Test(priority = 3)
    public void delete_row_from_table() throws ClassNotFoundException, SQLException {
        connections = this.getConnection();
        PreparedStatement preparedstatement = connections.prepareStatement("Delete from User where user_id = ?");
        preparedstatement.setInt(1, 200);
        preparedstatement.executeUpdate();
        get_table_data();
    }

    @AfterTest
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}