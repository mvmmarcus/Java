package com.github.dbs.repository;

import com.github.dbs.config.ConexaoFactory;

import java.sql.*;

public abstract class AbstractRepository {

    protected ResultSet executeQuery(String sql) {
        Connection conexao = null;
        PreparedStatement ps;

        try {
            conexao = ConexaoFactory.createConnection();
            conexao.setAutoCommit(false);

            if (sql.startsWith("SELECT")) {
                ps = conexao.prepareStatement(sql);
                ps.executeQuery();
            } else {
                String[] columnNames = new String[]{"id"};
                ps = conexao.prepareStatement(sql, columnNames);
                ps.executeUpdate();
            }

            conexao.commit();

            if (sql.startsWith("SELECT")) {
                return ps.getResultSet();
            } else {
                return ps.getGeneratedKeys();
            }
        } catch (SQLException e) {
            if (conexao != null) {
                try {
                    System.err.print("Rollback efetuado na transação.");
                    conexao.rollback();
                } catch(SQLException e2) {
                    System.err.print("SQLException: " + e2);
                }
            } else {
                System.err.print("SQLException: " + e);
            }
        } finally {
            try {
                if (conexao != null) {
                    conexao.setAutoCommit(true);
                }
            }  catch (SQLException e) {
                System.err.print("SQLException: " + e);
            }
        }

        return null;
    }
}
