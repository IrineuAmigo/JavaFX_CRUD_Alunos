package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlunosDAO {
    private static final Logger logger = Logger.getLogger(AlunosDAO.class.getName());

    public void cadastrarAluno(AlunosDTO alunosDTO) {
        String sql = "INSERT INTO alunos (nome, email, endereco, telefone, cidade) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, alunosDTO.getNome());
            pstm.setString(2, alunosDTO.getEmail());
            pstm.setString(3, alunosDTO.getEndereco());
            pstm.setString(4, alunosDTO.getTelefone());
            pstm.setString(5, alunosDTO.getCidade());

            pstm.executeUpdate();
            logger.info("Aluno cadastrado com sucesso.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar aluno", e);
        }
    }

    public ArrayList<AlunosDTO> listaAlunos() {
        // Adicionado ORDER BY para a tabela exibir os itens sempre na ordem certa
        String sql = "SELECT * FROM alunos ORDER BY id ASC";
        ArrayList<AlunosDTO> listaAluno = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                AlunosDTO aluno = new AlunosDTO();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setEndereco(rs.getString("endereco"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setCidade(rs.getString("cidade"));

                listaAluno.add(aluno);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar alunos", e);
        }
        return listaAluno;
    }

    public void alterarAluno(AlunosDTO alunosDTO) {
        String sql = "UPDATE alunos SET nome = ?, email = ?, endereco = ?, telefone = ?, cidade = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, alunosDTO.getNome());
            pstm.setString(2, alunosDTO.getEmail());
            pstm.setString(3, alunosDTO.getEndereco());
            pstm.setString(4, alunosDTO.getTelefone());
            pstm.setString(5, alunosDTO.getCidade());
            pstm.setInt(6, alunosDTO.getId());

            pstm.executeUpdate();
            logger.info("Aluno atualizado com sucesso.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao alterar aluno: " + e.getMessage());
        }
    }

    public void excluirAluno(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            pstm.executeUpdate();
            logger.info("Aluno excluído com sucesso ID: " + id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir aluno: " + e.getMessage());
        }
    }
}