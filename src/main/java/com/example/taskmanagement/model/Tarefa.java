package com.example.taskmanagement.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Tarefa {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty titulo = new SimpleStringProperty();
    private final StringProperty descricao = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dataEntrega = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();

    // ID
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Título
    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    // Descrição
    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    // Data de Entrega
    public LocalDate getDataEntrega() {
        return dataEntrega.get();
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega.set(dataEntrega);
    }

    public ObjectProperty<LocalDate> dataEntregaProperty() {
        return dataEntrega;
    }

    // Status
    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }
}
