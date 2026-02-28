package DataRepositories;

import Entities.Player;

import java.sql.*;
import java.util.ArrayList;

public class PlayerRepository {
    private final Connection connection;

    public PlayerRepository(Connection connection) {
        this.connection = connection;
    }

    public void loadPlayers(ArrayList<Player> players) {
        String sql = "SELECT * FROM players";
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Player p = new Player(
                        rs.getInt("id"),
                        rs.getString("playerName"),
                        rs.getInt("hp"),
                        rs.getInt("dmg"),
                        rs.getString("descript"),
                        rs.getInt("mana"),
                        rs.getInt("gold"),
                        rs.getInt("lvl"),
                        rs.getInt("room_id"),
                        rs.getInt("exp")
                );
                players.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPlayer(Player p) {
        String sql = "INSERT INTO players (playerName, hp, dmg, descript, mana, gold, lvl, room_id, exp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS)) {
            playerData(p, preparedStatement);

            preparedStatement.executeUpdate();
            ResultSet getKeys = preparedStatement.getGeneratedKeys();
            if (getKeys.next()) {
                p.setId(getKeys.getInt(1));
            }
        } catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }

    public void updatePlayer(Player p) {
        String sql = "UPDATE players SET playerName = ?, hp = ?, dmg = ?, descript = ?, mana = ?, gold = ?, lvl = ?,  room_id = ?, exp = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            playerData(p, preparedStatement);
            preparedStatement.setInt(10, p.getId());

            preparedStatement.executeUpdate();
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(p.getName() + " has been updated");
            } else  {
                System.out.println("No updates have been done");
            }
        } catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }

    private void playerData(Player p, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, p.getName());
        preparedStatement.setInt(2, p.getHP());
        preparedStatement.setInt(3, p.getDmg());
        preparedStatement.setString(4, p.getDescription());
        preparedStatement.setInt(5, p.getMana());
        preparedStatement.setInt(6, p.getGold());
        preparedStatement.setInt(7, p.getLevel());
        preparedStatement.setInt(8, p.getRoomId());
        preparedStatement.setInt(9, p.getExp());
    }

    public void deletePlayer(Player p) {
        {
            String sql = "DELETE FROM players WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, p.getId());
                preparedStatement.executeUpdate();
            }catch (SQLException sqlException){
                System.out.println(sqlException.getMessage() + " 1");
            }
        }

        {
            String sql = "ALTER TABLE players AUTO_INCREMENT = 1";
            try (Statement statement = connection.createStatement()){
                statement.executeUpdate(sql);
            } catch (SQLException sqlException){
                System.out.println(sqlException.getMessage() + " 2");
            }
        }
    }
}
