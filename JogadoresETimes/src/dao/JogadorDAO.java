package dao;

import model.Jogador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO {
    private Connection connection;

    public JogadorDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Jogador> listJogadores() {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogador";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String posicao = rs.getString("posicao");
                double altura = rs.getDouble("altura");
                int timeId = rs.getInt("time_id");
                jogadores.add(new Jogador(id, nome, idade, posicao, altura, timeId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jogadores;
    }

    public void addJogador(Jogador jogador) throws SQLException {
        String sql = "INSERT INTO jogador (nome, idade, posicao, altura, time_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, jogador.getNome());
            stmt.setInt(2, jogador.getIdade());
            stmt.setString(3, jogador.getPosicao());
            stmt.setDouble(4, jogador.getAltura());
            stmt.setInt(5, jogador.getTimeId());
            stmt.executeUpdate();
        }
    }

    public void updateJogador(Jogador jogador) throws SQLException {
        String sql = "UPDATE jogador SET nome = ?, idade = ?, posicao = ?, altura = ?, time_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, jogador.getNome());
            pstmt.setInt(2, jogador.getIdade());
            pstmt.setString(3, jogador.getPosicao());
            pstmt.setDouble(4, jogador.getAltura());
            pstmt.setInt(5, jogador.getTimeId());
            pstmt.setInt(6, jogador.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteJogador(int id) throws SQLException {
        String sql = "DELETE FROM jogador WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
