package com.example.taskmanagement.controller;

import com.example.taskmanagement.dao.TarefaDAO;
import com.example.taskmanagement.model.Tarefa;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableRow;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class TarefaController {

    @FXML private TableView<Tarefa> tabelaTarefas;
    @FXML private TableColumn<Tarefa, String> colunaTitulo;
    @FXML private TableColumn<Tarefa, String> colunaDescricao;
    @FXML private TableColumn<Tarefa, LocalDate> colunaDataEntrega;
    @FXML private TableColumn<Tarefa, String> colunaStatus;
    @FXML private TableColumn<Tarefa, Void> colunaAcoes;

    @FXML private TextField txtTitulo;
    @FXML private TextArea txtDescricao;
    @FXML private DatePicker dateEntrega;
    @FXML private ComboBox<String> comboStatus;
    @FXML private Label lblDataHora;

    private final TarefaDAO tarefaDAO = new TarefaDAO();
    private final ObservableList<Tarefa> listaTarefas = FXCollections.observableArrayList();
    private Timer timer;

    @FXML
    public void initialize() {
        comboStatus.setItems(FXCollections.observableArrayList("A Fazer", "Em Andamento", "Concluído"));

        colunaTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colunaDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        colunaDataEntrega.setCellValueFactory(cellData -> cellData.getValue().dataEntregaProperty());
        colunaStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        configurarColunas();
        atualizarLista();
        iniciarRelogio();

        tabelaTarefas.setRowFactory(tv -> {
            TableRow<Tarefa> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    if (row.isSelected()) {
                        tabelaTarefas.getSelectionModel().clearSelection();
                    } else {
                        tabelaTarefas.getSelectionModel().select(row.getItem());
                    }
                }
            });
            return row;
        });
    }

    private void configurarColunas() {
        colunaDataEntrega.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(formatter));
            }
        });

        colunaAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btnRemover = new Button("Remover");
            {
                btnRemover.setStyle("-fx-background-color: #FF5555; -fx-text-fill: white;");
                btnRemover.setOnAction(event -> {
                    Tarefa tarefa = getTableView().getItems().get(getIndex());
                    removerTarefa(tarefa);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnRemover);
            }
        });
    }

    @FXML
    private void removerTarefa(Tarefa tarefa) {
        if (tarefa != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Remover Tarefa");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Tem certeza de que deseja remover a tarefa selecionada?");
            Optional<ButtonType> resultado = confirmacao.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                try {
                    tarefaDAO.excluir(tarefa.getId());
                    atualizarLista();
                } catch (SQLException e) {
                    exibirErro("Erro ao remover a tarefa: " + e.getMessage());
                }
            }
        } else {
            exibirErro("Nenhuma tarefa selecionada.");
        }
    }

    private void iniciarRelogio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> lblDataHora.setText(LocalDateTime.now().format(formatter)));
            }
        }, 0, 1000);
    }

    @FXML
    public void salvarTarefa() {
        try {
            String titulo = txtTitulo.getText();
            String descricao = txtDescricao.getText();
            LocalDate dataEntregaValor = dateEntrega.getValue();
            String status = comboStatus.getValue();

            if (titulo == null || titulo.isEmpty() || dataEntregaValor == null || status == null) {
                exibirErro("Por favor, preencha todos os campos obrigatórios.");
                return;
            }

            Tarefa tarefa = new Tarefa();
            tarefa.setTitulo(titulo);
            tarefa.setDescricao(descricao);
            tarefa.setDataEntrega(dataEntregaValor);
            tarefa.setStatus(status);

            tarefaDAO.salvar(tarefa);
            atualizarLista();
            limparCampos();
        } catch (Exception e) {
            exibirErro("Erro ao salvar a tarefa: " + e.getMessage());
        }
    }

    @FXML
    private void atualizarLista() {
        try {
            listaTarefas.setAll(tarefaDAO.listar());
            tabelaTarefas.setItems(listaTarefas);
        } catch (SQLException e) {
            exibirErro("Erro ao carregar as tarefas: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtTitulo.clear();
        txtDescricao.clear();
        dateEntrega.setValue(null);
        comboStatus.setValue(null);
    }

    private void exibirErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
