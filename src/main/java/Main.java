import DataRepositories.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        AppService appService = getAppService();
        appService.startGame();
    }

    private static AppService getAppService() throws SQLException {
        var connection = DatabaseConnection.getConnection();
        MonsterRepository monsterRepository = new MonsterRepository(connection);
        RoomRepository roomRepository = new RoomRepository(connection);
        ItemRepository itemRepository = new ItemRepository(connection);
        PlayerRepository playerRepository = new PlayerRepository(connection);

        AppService appService = new AppService(monsterRepository, roomRepository, itemRepository,  playerRepository);

        appService.loadAllData();
        return appService;
    }
}
