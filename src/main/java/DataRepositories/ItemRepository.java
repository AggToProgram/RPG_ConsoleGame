package DataRepositories;

import Entities.Player;
import Items.Item;
import Items.Potion;
import Items.Weapon;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemRepository {
    private final Connection connection;

    public ItemRepository(Connection connection) {
        this.connection = connection;
    }

    public void loadItems(ArrayList<Item> items) throws SQLException {
        String sql = "SELECT * FROM items";
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String type = rs.getString("itemType");
                if (type.equalsIgnoreCase("Sword")){
                    Item i = new Weapon(
                            rs.getInt("id"),
                            rs.getString("itemName"),
                            rs.getString("descript"),
                            rs.getString("itemType"),
                            rs.getInt("dmg"),
                            rs.getInt("manaDmg")
                    );
                    items.add(i);
                } else if (type.equalsIgnoreCase("Potion")){
                    Item i = new Potion(
                            rs.getInt("id"),
                            rs.getString("itemName"),
                            rs.getString("descript"),
                            rs.getString("itemType"),
                            rs.getInt("dmg"),
                            rs.getString("buffType")
                    );
                    items.add(i);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " 4");
        }
    }

    public void loadInventory(Player player, ArrayList<Item> items) {
        player.getItems().clear();

//        Map<Integer, Item> itemMap = new HashMap<>();
//        for (Item item : items) {
//            itemMap.put(item.getId(), item);
//        }

        String sql = "SELECT item_id FROM playerInventory WHERE player_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, player.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                Item item = items.get(itemId);

                if (item != null) {
                    player.getItems().add(item);
                }
            }
            System.out.println("Loaded inventory! Total items: " + player.getItems().size());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " 5");
        }
    }

    public void updateInventory(Player player, ArrayList<Item> items) throws SQLException {
        String sql = "INSERT INTO playerInventory (player_id, item_id) VALUES (?, ?)";
        String sql2 = "DELETE FROM playerInventory WHERE player_id = ?";

        try {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)){
                preparedStatement.setInt(1, player.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage() + " 5");
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                for (Item item : items) {
                    preparedStatement.setInt(1, player.getId());
                    preparedStatement.setInt(2, item.getId());

                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage() + " 5");
            }
            connection.commit();
        } catch (SQLException e){
            System.out.println(e.getMessage() + " 5");
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void deleteFromInventory(Player player, Item item) throws SQLException {
        String sql = "DELETE FROM playerInventory WHERE player_id = ? AND item_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, player.getId());
            preparedStatement.setInt(2, item.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " 6");
        }
    }
}
