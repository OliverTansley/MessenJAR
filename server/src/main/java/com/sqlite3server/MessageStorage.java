package com.sqlite3server;

// CONNECTION STRING
// "jdbc:sqlite:src/main/java/com/sqlite3server/database/Messages.db"

import java.sql.*;

public class MessageStorage {

    Connection conn;

    public MessageStorage(String urlString) {
        try {
            conn = DriverManager.getConnection(urlString);
        } catch (SQLException e) {
            System.out.println("Cannot find message backup data");
        }
    }

    public static void showMessages(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Messages");
            while (rs.next()) {
                System.out.println(rs.getString("name") + ":" + rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMessage(Connection conn, String m) {
        try {
            Statement stmt = conn.createStatement();
            // TODO - split string at ":"
            stmt.execute("INSERT INTO Messages (name,message) VALUES ('" + m + "','" + m + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
