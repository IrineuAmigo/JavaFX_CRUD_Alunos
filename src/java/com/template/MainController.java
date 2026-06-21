package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class MainController {

    @FXML private Button btnLimpar;
    @FXML private Button btnSalvar;
    @FXML private Button btnExcluir;
    @FXML private Button btnAlterar;
    @FXML private Button btnCadastrar;

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtCidade;

    @FXML private TableView<AlunosDTO> tblAlunos;

    @FXML private TableColumn<AlunosDTO, Integer> colId;
    @FXML private TableColumn<AlunosDTO, String> colNome;
    @FXML private TableColumn<AlunosDTO, String> colEmail;
    @FXML private TableColumn<AlunosDTO, String> colEndereco;
    @FXML private TableColumn<AlunosDTO, String> colTelefone;
    @FXML private TableColumn<AlunosDTO, String> colCidade;

    // =========================
    // INIT (Executado ao abrir a tela)
    // =========================
    @FXML
    public void initialize() {
        // 1. Configura as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        // 2. Desativa botões que dependem da seleção da tabela
        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);

        // 3. Carrega os dados do banco
        carregarTabela();

        // 4. Adiciona o evento de CLIQUE na tabela para carregar os campos
        tblAlunos.getSelectionModel().selectedItemProperty().addListener(
                (observador, selecaoAntiga, selecaoNova) -> {
                    if (selecaoNova != null) {
                        carregarCampos();          // Chama o método que preenche os TextField

                        // Habilita os botões de edição e desabilita os de cadastro
                        btnAlterar.setDisable(false);
                        btnExcluir.setDisable(false);
                        btnSalvar.setDisable(true);
                        btnCadastrar.setDisable(true);
                    }
                }
        );
    }

    // =========================
    // CARREGAR TABELA E CAMPOS
    // =========================
    private void carregarTabela() {
        AlunosDAO dao = new AlunosDAO();
        List<AlunosDTO> listaAlunos = dao.listaAlunos();
        ObservableList<AlunosDTO> dadosObservaveis = FXCollections.observableArrayList(listaAlunos);
        tblAlunos.setItems(dadosObservaveis);
    }

    @FXML
    private void carregarCampos() {
        AlunosDTO obj = tblAlunos.getSelectionModel().getSelectedItem();
        if (obj != null) {
            txtNome.setText(obj.getNome());
            txtEmail.setText(obj.getEmail());
            txtEndereco.setText(obj.getEndereco());
            txtTelefone.setText(obj.getTelefone());
            txtCidade.setText(obj.getCidade());
        }
    }

    // =========================
    // LIMPAR CAMPOS & FOCO
    // =========================
    @FXML
    private void btnLimparAction(ActionEvent event) {
        txtNome.clear();
        txtEmail.clear();
        txtEndereco.clear();
        txtTelefone.clear();
        txtCidade.clear();

        // Remove a seleção da tabela
        tblAlunos.getSelectionModel().clearSelection();

        // Restaura os botões ao estado inicial
        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);
        btnSalvar.setDisable(false);
        btnCadastrar.setDisable(false);

        txtNome.requestFocus();
    }

    // =========================
    // CADASTRAR / SALVAR
    // =========================
    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        // Redirecionando para aproveitar a mesma lógica do botão Salvar
        btnSalvarAction(event);
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Por favor, preencha pelo menos Nome e Email.");
            alerta.showAndWait();
            return;
        }

        AlunosDTO dto = new AlunosDTO();
        dto.setNome(txtNome.getText().trim());
        dto.setEmail(txtEmail.getText().trim());
        dto.setEndereco(txtEndereco.getText().trim());
        dto.setTelefone(txtTelefone.getText().trim());
        dto.setCidade(txtCidade.getText().trim());

        new AlunosDAO().cadastrarAluno(dto);

        carregarTabela();
        btnLimparAction(null); // Limpa os campos após salvar

        Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Aluno cadastrado com sucesso!");
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    // =========================
    // ALTERAR
    // =========================
    @FXML
    private void btnAlterarAction(ActionEvent event) {
        AlunosDTO selecionado = tblAlunos.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Nome e Email não podem ficar em branco.");
            alerta.showAndWait();
            return;
        }

        AlunosDTO dto = new AlunosDTO();
        dto.setId(selecionado.getId());
        dto.setNome(txtNome.getText().trim());
        dto.setEmail(txtEmail.getText().trim());
        dto.setEndereco(txtEndereco.getText().trim());
        dto.setTelefone(txtTelefone.getText().trim());
        dto.setCidade(txtCidade.getText().trim());

        new AlunosDAO().alterarAluno(dto);

        carregarTabela();
        btnLimparAction(null);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Cadastro atualizado com sucesso!");
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    // =========================
    // EXCLUIR
    // =========================
    @FXML
    private void btnExcluirAction(ActionEvent event) {
        AlunosDTO selecionado = tblAlunos.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente excluir o aluno '" + selecionado.getNome() + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            new AlunosDAO().excluirAluno(selecionado.getId());

            carregarTabela(); // Recarrega a tabela sem o aluno excluído
            btnLimparAction(null);
        }
    }
}