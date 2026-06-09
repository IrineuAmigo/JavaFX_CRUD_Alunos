package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.util.ArrayList;

public class MainController {

    @FXML private Button btnSalvar;
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtCidade;


        @FXML private TableView<AlunosDTO> tblAlunos;
        @FXML private TableColumn<AlunosDTO, String> colNome;
        @FXML private TableColumn<AlunosDTO, String> colEmail;
        @FXML private TableColumn<AlunosDTO, String> colEndereco;
        @FXML private TableColumn<AlunosDTO, String> colTelefone;
        @FXML private TableColumn<AlunosDTO, String> colCidade;

        @FXML
        private void btnCadastrarAction(ActionEvent event){
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String endereco = txtEndereco.getText();
            String telefone = txtTelefone.getText();
            String cidade = txtCidade.getText();

            AlunosDTO objAlunosDTO = new AlunosDTO();
            objAlunosDTO.setNome(nome);
            objAlunosDTO.setEmail(email);
            objAlunosDTO.setEndereco(endereco);
            objAlunosDTO.setTelefone(telefone);
            objAlunosDTO.setCidade(cidade);

            AlunosDAO objAlunosDAO = new AlunosDAO();
            objAlunosDAO.cadastrarAluno(objAlunosDTO);
            cadastrarAluno();

        }

        @FXML
        private void cadastrarAluno(){
            AlunosDAO objAlunosDAO = new AlunosDAO();
            ArrayList<AlunosDTO> cadastrarAluno = objAlunosDAO.cadastrarAluno();
            tblAlunos.setItems( FXCollections.observableArrayList(cadastrarAluno));
        }

        @FXML
        private void atualizarAluno() {
            AlunosDAO objAlunosDAO = new AlunosDAO();
            ArrayList<AlunosDTO> atualizarAluno = objAlunosDAO.atualizarAluno();
            tblAlunos.setItems( FXCollections.observableArrayList(atualizarAluno));

        }

        @FXML
        private void excluirAluno(int id) {
            AlunosDAO objAlunosDAO = new AlunosDAO();
            ArrayList<AlunosDTO> excluirAluno = objAlunosDAO.excluirAluno(int id);
            tblAlunos.setItems( FXCollections.observableArrayList(excluirAluno));

        }


        private void initialize()
    {
        colId.setCelValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCelValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCelValueFactory(new PropertyValueFactory<>("email"));
        colEndereco.setCelValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCelValueFactory(new PropertyValueFactory<>("telefone"));
        colCidade.setCelValueFactory(new PropertyValueFactory<>("cidade"));

        carregarAlunos();
    }


    @FXML
    private void carregarCampos(){
            AlunosDTO objAlunosDTO = tblAlunos.getSelectionModel().getSelectedItem();

            if (objAlunosDTO != null){

                txtNome.setText(objAlunosDTO.getNome());
                txtEmail.setText(objAlunosDTO.getEmail());
                txtEndereco.setText(objAlunosDTO.getEndereco());
                txtTelefone.setText(objAlunosDTO.getTelefone());
                txtCidade.setText(objAlunosDTO.getCidade());

            }
    }
}





