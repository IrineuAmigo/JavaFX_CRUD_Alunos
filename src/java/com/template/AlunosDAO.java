package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunosDAO {


    public void cadastrarAluno(AlunosDTO alunosDTO) {  // Cadastro de Alunos

        String sql = "INSERT INTO alunos (nome, email, endereco, telefone, cidade) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, alunosDTO.getNome());
            pstm.setString(2, alunosDTO.getEmail());
            pstm.setString(3, alunosDTO.getEndereco());
            pstm.setString(4, alunosDTO.getTelefone());
            pstm.setString(5, alunosDTO.getCidade());

            pstm.executeUpdate();

            System.out.println("\nAluno cadastrado com sucesso!\n");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    public void listarAlunos() {        // Listagem de Alunos Cadastrados no Banco de Dados

        String sql = "SELECT * FROM alunos";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            System.out.println("\n=== LISTA DE ALUNOS ===\n");

            while (rs.next()) {

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Endereço: " + rs.getString("endereco"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Cidade: " + rs.getString("cidade"));
                System.out.println("----------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }
    }

    public void atualizarAluno(AlunosDTO alunosDTO) {       // Atualização do Cadasto

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

            System.out.println("\nAluno atualizado com sucesso!\n");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    public void excluirAluno(int id) {      // Exclusão de Alunos Cadastados por ID

        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            pstm.executeUpdate();

            System.out.println("\nAluno excluído com sucesso!\n");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
        }
    }

    public void buscarAlunoPorNome(String nome) {       // Buscar por NOME de ALUNO

        String sql = "SELECT * FROM alunos WHERE nome ILIKE ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, "%" + nome + "%");
            ResultSet rs = pstm.executeQuery();

            System.out.println("\n=== RESULTADO DA BUSCA ===\n");

            while (rs.next()) {

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Endereço: " + rs.getString("endereco"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Cidade: " + rs.getString("cidade"));
                System.out.println("------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }
    }

    public void totalAlunos() {     // Lista Total de Alunos Cadastrados

        String sql = "SELECT COUNT(*) AS total FROM alunos";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("\nTOTAL de alunos CADASTRADOS: " + total);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao contar alunos: " + e.getMessage());
        }
    }


}
