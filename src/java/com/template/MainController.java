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

    // Convertido para ComboBox conforme os requisitos de UX
    @FXML private ComboBox<String> cbCidade;

    // Componentes de Turno inclusos (UX)
    @FXML private RadioButton rbDiurno;
    @FXML private RadioButton rbNoturno;
    @FXML private ToggleGroup tgTurno;

    // Elementos de Feedback Visual (UI/UX)
    @FXML private Label lblMensagem;
    @FXML private Label lblTotalDiurno;
    @FXML private Label lblTotalNoturno;
    @FXML private Label lblTotalGeral;

    @FXML private TableView<AlunosDTO> tblAlunos;

    @FXML private TableColumn<AlunosDTO, Integer> colId;
    @FXML private TableColumn<AlunosDTO, String> colNome;
    @FXML private TableColumn<AlunosDTO, String> colEmail;
    @FXML private TableColumn<AlunosDTO, String> colEndereco;
    @FXML private TableColumn<AlunosDTO, String> colTelefone;
    @FXML private TableColumn<AlunosDTO, String> colCidade;

    @FXML
    public void initialize() {
        // Configura as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        // Popula as opções do ComboBox de Cidades (UX Bônus)
        cbCidade.setItems(FXCollections.observableArrayList(
                "Bauru", "São Paulo", "Botucatu", "Jaú", "Marília", "Piratininga"
        ));

        // UX: Impede que o usuário digite letras no campo de Telefone
        txtTelefone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTelefone.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Evento de clique na tabela
        tblAlunos.getSelectionModel().selectedItemProperty().addListener(
                (observador, selecaoAntiga, selecaoNova) -> {
                    if (selecaoNova != null) {
                        carregarCampos();
                        btnAlterar.setDisable(false);
                        btnExcluir.setDisable(false);
                        btnSalvar.setDisable(true);
                        btnCadastrar.setDisable(true);
                    }
                }
        );

        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);

        carregarTabela();
    }

    private void carregarTabela() {
        AlunosDAO dao = new AlunosDAO();
        List<AlunosDTO> listaAlunos = dao.listaAlunos();
        ObservableList<AlunosDTO> dadosObservaveis = FXCollections.observableArrayList(listaAlunos);
        tblAlunos.setItems(dadosObservaveis);

        atualizarContadores(listaAlunos);
    }

    // UX: Atualiza dinamicamente os três contadores no rodapé da tabela
    private void atualizarContadores(List<AlunosDTO> lista) {
        int total = lista.size();
        long diurno = lista.stream().filter(a -> "Diurno".equalsIgnoreCase(a.getTurno())).count();
        long noturno = lista.stream().filter(a -> "Noturno".equalsIgnoreCase(a.getTurno())).count();

        lblTotalGeral.setText("Total de Alunos Cadastrados: " + total);
        lblTotalDiurno.setText("Total Alunos Diurno: " + diurno);
        lblTotalNoturno.setText("Total Alunos Noturno: " + noturno);
    }

    @FXML
    private void carregarCampos() {
        AlunosDTO obj = tblAlunos.getSelectionModel().getSelectedItem();
        if (obj != null) {
            txtNome.setText(obj.getNome());
            txtEmail.setText(obj.getEmail());
            txtEndereco.setText(obj.getEndereco());
            txtTelefone.setText(obj.getTelefone());
            cbCidade.setValue(obj.getCidade());

            if ("Noturno".equalsIgnoreCase(obj.getTurno())) {
                rbNoturno.setSelected(true);
            } else {
                rbDiurno.setSelected(true);
            }
            limparEstilosDeErro();
        }
    }

    // UI: Reseta as bordas vermelhas e limpa os alertas
    private void limparEstilosDeErro() {
        txtNome.setStyle("");
        txtEmail.setStyle("");
        lblMensagem.setText("");
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        txtNome.clear();
        txtEmail.clear();
        txtEndereco.clear();
        txtTelefone.clear();
        cbCidade.setValue(null);
        rbDiurno.setSelected(true);

        tblAlunos.getSelectionModel().clearSelection();
        limparEstilosDeErro();

        btnAlterar.setDisable(true);
        btnExcluir.setDisable(true);
        btnSalvar.setDisable(false);
        btnCadastrar.setDisable(false);

        txtNome.requestFocus();
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        btnSalvarAction(event);
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        limparEstilosDeErro();
        boolean erro = false;

        // UI/UX: Validação e destaque de borda vermelha para campos vazios
        if (txtNome.getText().trim().isEmpty()) {
            txtNome.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            erro = true;
        }

        String email = txtEmail.getText().trim();
        if (email.isEmpty() || !email.contains("@") || !email.contains(".com")) {
            txtEmail.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            erro = true;
        }

        if (erro) {
            lblMensagem.setStyle("-fx-text-fill: #ff3333;");
            lblMensagem.setText("⚠️ Verifique os campos destacados em vermelho!");
            return;
        }

        AlunosDTO dto = new AlunosDTO();
        dto.setNome(txtNome.getText().trim());
        dto.setEmail(email);
        dto.setEndereco(txtEndereco.getText().trim());
        dto.setTelefone(txtTelefone.getText().trim());
        dto.setCidade(cbCidade.getValue() != null ? cbCidade.getValue() : "");
        dto.setTurno(rbDiurno.isSelected() ? "Diurno" : "Noturno");

        new AlunosDAO().cadastrarAluno(dto);

        carregarTabela();
        btnLimparAction(null);

        // UI: Mensagem amigável de sucesso diretamente no Label
        lblMensagem.setStyle("-fx-text-fill: #2dfc53;");
        lblMensagem.setText("✨ Aluno matriculado com sucesso na Notre Vie!");
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        AlunosDTO selecionado = tblAlunos.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        limparEstilosDeErro();
        boolean erro = false;

        if (txtNome.getText().trim().isEmpty()) {
            txtNome.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            erro = true;
        }

        String email = txtEmail.getText().trim();
        if (email.isEmpty() || !email.contains("@") || !email.contains(".com")) {
            txtEmail.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            erro = true;
        }

        if (erro) {
            lblMensagem.setStyle("-fx-text-fill: #ff3333;");
            lblMensagem.setText("⚠️ Alteraçao recusada. Corrija os erros cadastrais.");
            return;
        }

        AlunosDTO dto = new AlunosDTO();
        dto.setId(selecionado.getId());
        dto.setNome(txtNome.getText().trim());
        dto.setEmail(email);
        dto.setEndereco(txtEndereco.getText().trim());
        dto.setTelefone(txtTelefone.getText().trim());
        dto.setCidade(cbCidade.getValue() != null ? cbCidade.getValue() : "");
        dto.setTurno(rbDiurno.isSelected() ? "Diurno" : "Noturno");

        new AlunosDAO().alterarAluno(dto);

        carregarTabela();
        btnLimparAction(null);

        lblMensagem.setStyle("-fx-text-fill: #2dfc53;");
        lblMensagem.setText("✨ Ficha de matrícula atualizada com sucesso!");
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        AlunosDTO selecionado = tblAlunos.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Matrícula");
        alert.setHeaderText(null);
        alert.setContentText("Deseja cancelar a matrícula do aluno(a) '" + selecionado.getNome() + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            new AlunosDAO().excluirAluno(selecionado.getId());

            carregarTabela();
            btnLimparAction(null);

            lblMensagem.setStyle("-fx-text-fill: #ffff00;");
            lblMensagem.setText("🗑️ Matrícula removida do sistema da escola.");
        }
    }
}