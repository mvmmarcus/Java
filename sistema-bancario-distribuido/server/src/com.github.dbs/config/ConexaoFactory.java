package com.github.dbs.config;

import java.sql.*;

public class ConexaoFactory {

    public static Connection createConnection() throws SQLException{
        String url = "jdbc:postgresql://localhost:5432/dbs";
        String user = "postgres";
        String password = "postgres";

        return DriverManager.getConnection(url, user, password);
    }
}
