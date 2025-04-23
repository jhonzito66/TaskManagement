package com.example.taskmanagement.dao;

import com.example.taskmanagement.model.Tarefa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    // Listar todas as tarefas
    public List<Tarefa> listar() throws SQLException {
        String sql = "SELECT * FROM tarefas";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Tarefa> tarefas = new ArrayList<>();
            while (rs.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setDataEntrega(rs.getDate("data_entrega").toLocalDate());
                tarefa.setStatus(rs.getString("status"));
                tarefas.add(tarefa);
            }
            return tarefas;
        }
    }

    // Salvar uma nova tarefa
    public void salvar(Tarefa tarefa) throws SQLException {
        if (tarefa.getTitulo() == null || tarefa.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Título' é obrigatório.");
        }

        String sql = "INSERT INTO tarefas (titulo, descricao, data_entrega, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, tarefa.getDataEntrega() != null ? Date.valueOf(tarefa.getDataEntrega()) : null);
            stmt.setString(4, tarefa.getStatus());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Recuperando o id gerado para a tarefa inserida
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        tarefa.setId(generatedKeys.getInt(1));  // Definindo o id gerado
                    }
                }
            }
        }
    }

    // Atualizar uma tarefa existente
    public void atualizar(Tarefa tarefa) throws SQLException {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, data_entrega = ?, status = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setDate(3, Date.valueOf(tarefa.getDataEntrega()));
            stmt.setString(4, tarefa.getStatus());
            stmt.setInt(5, tarefa.getId());  // Garantindo que o id seja passado corretamente
            stmt.executeUpdate();
        }
    }

    // Excluir uma tarefa pelo id
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);  // Passando o id para deletar
            stmt.executeUpdate();
        }
    }
}
