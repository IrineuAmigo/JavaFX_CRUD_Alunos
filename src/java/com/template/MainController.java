
package com.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private TextField txtId;
    @FXML private TableView<AlunosDTO>  tblAlunos;
    @FXML private TableColumn<AlunosDTO, String> colNome;
    @FXML private TableColumn<AlunosDTO, String> colEmail;
    @FXML private TableColumn<AlunosDTO, String> colEndereco;
    @FXML private TableColumn<AlunosDTO, String> colTelefone;
    @FXML private TableColumn<AlunosDTO, String> colCidade;




    @FXML

    private void btnCadastrarAction(ActionEvent event){
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String endereco = textEndereco.getText();
        String telefone = textTelefone.getText();
        String cidade = textCidade.getText();

        AlunoDTO objalunodto = new AlunoDTO();
        objalunodto.setNome(nome);
        objalunodto.setEmail(email);
        objalunodto.setEndereco(endereco);
        objalunodto.setTelefone(telefone);
        objalunodto.setCidade(cidade);

        AlunosDAO objalunodao = new AlunosDAO();
        objalunodao.cadastrarAluno(objalunodto);

        carregarAlunos();

    }

    private void carregarAlunos(){
        AlunosDAO objqAlunoDAO = new AlunosDAO();
        ArrayList<AlunosDTO> listaAlunos = objAlunoDAO.listarAlunos();
        tblAlunos.setItems (FXCollections.observableArrayList(listaAlunos));
    }


    private void atualizarAluno() {
       AlunosDAO objAlunosDAO = new AlunosDAO();
       ArrayList<AlunosDTO> atualizarAluno = objAlunosDAO.atualizarAluno();
       tbtAlunos.setItems (FXCollections.observableArrayList(atualizaAlunos));
    }


    private void excluirAluno(){
            AlunosDAO objAlunosDAO = new AlunosDAO();
            ArrayList<AlunosDTO> excluirAluno = objAlunosDAO.excluirAluno();
            tbtAlunos.setItems (FXCollections.observableArrayList(excluirAluno));

        }
    }

    private void initialize()
    {
        colId.setCelValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCelValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCelValueFactory(new PropertyValueFactory<>("email"));
        colEndereco.setCelValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCelValueFactory(new PropertyValueFactory<>("telefone"));
        colCidade.setCelValueFactory(new PropertyValueFactory<>("cidade"));

        cadstrarAlunos();


    }

