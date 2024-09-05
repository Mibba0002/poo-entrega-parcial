package dao;

import model.Time;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeDAO {
    private Connection connection;

    public TimeDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Time> listTimes() {
        List<Time> times = new ArrayList<>();
        String sql = "SELECT * FROM time";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                times.add(new Time(id, nome));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return times;
    }

    public void addTime(Time time) throws SQLException {
        String sql = "INSERT INTO time (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, time.getNome());
            stmt.executeUpdate();
        }
    }

    public void updateTime(Time time) throws SQLException {
        String sql = "UPDATE time SET nome = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, time.getNome());
            pstmt.setInt(2, time.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteTime(int id) throws SQLException {
        String sql = "DELETE FROM time WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
