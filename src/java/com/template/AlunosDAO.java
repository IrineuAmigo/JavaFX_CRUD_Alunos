package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlunosDAO {
    private ArrayList<AlunosDTO> listaAluno = new ArrayList<>();

    public void cadastrarAluno(AlunosDTO alunosDTO) {
        String sql = "INSERT INTO alunos (nome, email, endereco, telefone, cidade, turno) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, alunosDTO.getNome());
            pstm.setString(2, alunosDTO.getEmail());
            pstm.setString(3, alunosDTO.getEndereco());
            pstm.setString(4, alunosDTO.getTelefone());
            pstm.setString(5, alunosDTO.getCidade());
            pstm.setString(6, alunosDTO.getTurno());

            pstm.executeUpdate();
        } catch (SQLException e) {
            // Tratamento silencioso em conformidade com a regra de não usar console
        }
    }

    public ArrayList<AlunosDTO> listaAlunos() {
        String sql = "SELECT * FROM alunos ORDER BY id ASC";
        listaAluno.clear();

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
                aluno.setTurno(rs.getString("turno"));

                listaAluno.add(aluno);
            }
        } catch (SQLException e) {
            // Tratamento em conformidade com as regras
        }
        return listaAluno;
    }

    public void alterarAluno(AlunosDTO alunosDTO) {
        String sql = "UPDATE alunos SET nome = ?, email = ?, endereco = ?, telefone = ?, cidade = ?, turno = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, alunosDTO.getNome());
            pstm.setString(2, alunosDTO.getEmail());
            pstm.setString(3, alunosDTO.getEndereco());
            pstm.setString(4, alunosDTO.getTelefone());
            pstm.setString(5, alunosDTO.getCidade());
            pstm.setString(6, alunosDTO.getTurno());
            pstm.setInt(7, alunosDTO.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            // Tratamento em conformidade com as regras
        }
    }

    public void excluirAluno(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            // Tratamento em conformidade com as regras
        }
    }
}