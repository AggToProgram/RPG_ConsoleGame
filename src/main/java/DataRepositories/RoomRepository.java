package DataRepositories;

import Rooms.Room;

import java.sql.*;
import java.util.ArrayList;

public class RoomRepository {
    private final Connection connection;

    public RoomRepository(Connection connection) {
        this.connection = connection;
    }

    public void loadRooms(ArrayList<Room> rooms) {
        String sql = "SELECT * FROM rooms";
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Room r = new Room(
                        rs.getInt("id"),
                        rs.getString("roomName"),
                        rs.getString("descript"),
                        rs.getInt("requiredLvl")
                );
                rooms.add(r);
            }
        } catch (SQLException sqlException){
            System.out.println(sqlException.getMessage() + " 2");
        }
    }
}
