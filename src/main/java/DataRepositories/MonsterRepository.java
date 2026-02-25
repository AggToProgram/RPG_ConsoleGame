package DataRepositories;

import Entities.Monster;

import java.sql.*;
import java.util.ArrayList;

public class MonsterRepository {
    private final Connection connection;

    public MonsterRepository(Connection connection) {
        this.connection = connection;
    }

    public void loadMonsters(ArrayList<Monster> monsters) throws SQLException {
        String sql = "SELECT * FROM monsters";
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Monster m = new Monster(
                        rs.getInt("id"),
                        rs.getString("monsterName"),
                        rs.getInt("hp"),
                        rs.getInt("dmg"),
                        rs.getString("descript"),
                        rs.getString("monsterType"),
                        rs.getInt("ogHp")
                );
                monsters.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " 3");
        }
    }
}
