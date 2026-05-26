
package com.template;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private TextField txtId;
    @FXML private TableView<AlunosDTO>  tblAlunos;
    @FXML private TableColumn<AlunosDtO, String> colNome;
    @FXML private TableColumn<AlunosDTO, String> colEmail;
    @FXML private TableColumn<AlunosDTO, String> colEndereco;
    @FXML private TableColumn<AlunosDTO, String> colTelefone;
    @FXML private TableColumn<AlunosDTO, String> colCidade;




    @FXML
    private void initialize()
    {
        System.out.println("FXML loaded successfully!");
    }
}