package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por realizar a conexão com o banco de dados PostgreSQL.
 */
public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/db_crud";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            // Imprime o erro real no console para sabermos o que aconteceu
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco: " + e.getMessage(), e);
        }
    }
}
