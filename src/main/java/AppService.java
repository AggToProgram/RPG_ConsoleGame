import DataRepositories.*;
import Entities.Monster;
import Entities.Player;
import Items.Item;
import Rooms.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppService {

    private final MonsterRepository monsterRepository;
    private final RoomRepository roomRepository;
    private final ItemRepository itemRepository;
    private final PlayerRepository playerRepository;

    public static final Scanner sc = new Scanner(System.in);

    private static final ArrayList<Monster> monsters = new ArrayList<>();
    private static final ArrayList<Room> rooms = new ArrayList<>();
    private static final ArrayList<Item> items = new ArrayList<>();
    private static final ArrayList<Player> players = new ArrayList<>();

    public AppService(MonsterRepository monsterRepository,  RoomRepository roomRepository,  ItemRepository itemRepository, PlayerRepository playerRepository) {
        this.monsterRepository = monsterRepository;
        this.roomRepository = roomRepository;
        this.itemRepository = itemRepository;
        this.playerRepository = playerRepository;
    }

    public void loadAllData() throws SQLException {
        monsterRepository.loadMonsters(monsters);
        roomRepository.loadRooms(rooms);
        itemRepository.loadItems(items);
        playerRepository.loadPlayers(players);
    }

    public void startGame() throws SQLException {

        System.out.println("Welcome to the Doravas Dungeon!");
        System.out.println("Are you a new or existing player?");
        System.out.println("1: New Player");
        System.out.println("2: Existing Player");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice == 1) {

            boolean running = true;
            while (running) {
                System.out.println("What is your name?");
                String name = sc.nextLine();
                boolean exists = false;

                for (Player p : players) {
                    if (p.getName().equals(name)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("Pick a different player name, this one already exists.");
                } else {
                    System.out.println("Welcome " + name + "! Tell us about yourself");
                    String description = sc.nextLine();

                    Player player = new Player(name, 100, 25, description, 200, 0, 1, 1, 0);
                    playerRepository.addPlayer(player);
                    players.add(player);

                    System.out.println("You have entered the Doravas dungeon, the exit is blocked, you are clearly trapped.");
                    System.out.println("You are in a room with some statues that look abstract.");
                    System.out.println(rooms.getFirst().toString());

                    game(player);   // ← go directly to game
                    running = false;
                }
            }
        } else if (choice == 2) {
            if (players.isEmpty()) {
                System.out.println("There are no players in the database!");
            } else {
                boolean running = true;
                while (running) {
                    System.out.println("What was your name again?");
                    String name = sc.nextLine();
                    Player foundPlayer = null;

                    for (Player p : players) {
                        if (p.getName().equals(name)) {
                            foundPlayer = p;
                            break;
                        }
                    }

                    if (foundPlayer != null) {
                        System.out.println("Welcome back! " + foundPlayer.getName());
                        itemRepository.loadInventory(foundPlayer, items);
                        game(foundPlayer);
                        running = false;
                    } else {
                        System.out.println("Not a valid player name! Try again.");
                    }
                }
            }
        }
    }

    public void game(Player player) throws SQLException {
        boolean running = true;
        while(running){
            System.out.println("What do you wish to do?");
            System.out.println("1: Move");
            System.out.println("2: Explore");
            System.out.println("3: Use item");
            System.out.println("4: Your stats");
            System.out.println("5: Exit game");
            System.out.println("6: Delete player");
            int input = Integer.parseInt(sc.nextLine());

            switch (input) {
                case 1 -> playerMove(player);
                case 2 -> running = playerExplore(player);
                case 3 -> useItem(player);
                case 4 -> System.out.println(player.getStats());
                case 5 -> {
                    playerRepository.updatePlayer(player);
                    itemRepository.updateInventory(player, player.getItems());
                    running = false;
                }
                case  6 -> {
                    System.out.println(player.getName() + " " + player.getId());
                    playerRepository.deletePlayer(player);
                    players.remove(player);
                    running = false;
                }
            }
        }
    }

    //Player Service
    public boolean playerExplore(Player player){

        if (player.getRoomId() == 1){
            System.out.println("Nothing to explore here");
            return true;
        }

        int probability = (int)(Math.random() * 100);
        if (probability < 40) {
            return monsterApparition(randomMonster(player), player);
        } else if (probability < 80) {
            itemApparition(randomItem(), player);
        } else {
            System.out.println("Nothing has been found!");
        }
        return true;
    }

    public void playerMove(Player player){
        System.out.println("Where do you wish to move?");
        System.out.println("1: Next room");
        System.out.println("2: Previous room");
        int direction = Integer.parseInt(sc.nextLine());

        int currentRoomId = player.getRoomId();
        System.out.println("Current Room Id: " + currentRoomId);
        int targetId;
        if (direction == 1){
            targetId = currentRoomId + 1;
        }  else if (direction == 2){
            targetId = currentRoomId - 1;
        } else {
            System.out.println("Invalid direction");
            return;
        }

        if (targetId < 1 || targetId >= rooms.size()) {
            System.out.println("You can't go that way.");
            return;
        }

        Room targetRoom = rooms.get(targetId - 1);
        System.out.println("Target Room: " + targetRoom.getId());

        if (player.getLevel() < targetRoom.getRequiredLvl()) {
            System.out.println("You need to level up to level " + targetRoom.getRequiredLvl() + " to enter this room!");
            return;
        }

        player.setRoomId(targetId);
        System.out.println("Welcome to: ");
        System.out.println(targetRoom.toString());
    }

    public void useItem(Player player) {
        ArrayList<Item> items = player.getItems();
        if(items.isEmpty()){
            System.out.println("No items in your hand");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ": " + items.get(i).getName());
        }

        System.out.println("What item would you like to use? Type 0 to cancel.");
        int itemIndex = Integer.parseInt(sc.nextLine());

        if(itemIndex == 0){
            System.out.println("No items used");
            return;
        } else if(itemIndex < 0 || itemIndex > items.size()){
            System.out.println("Invalid choice");
            return;
        }

        Item foundItem = items.get(itemIndex - 1);
        foundItem.itemEffect(player);
        System.out.println("You have used " + foundItem.getName() + ".");
        itemRepository.deleteFromInventory(player, foundItem);
        items.remove(foundItem);
    }

    public void nextLevel(Player player){
        if (player.getExp() == 100){
            player.setExp(0);
            player.setLevel(player.getLevel() + 1);
            System.out.println("YOU LEVELED UP!");
            System.out.println("You are now Level " + player.getLevel());
        }
    }

    public void addExp(Monster monster, Player player){
        int exp = player.getExp();
        if (monster.equals(monsters.getFirst())){
            player.setExp(exp+10);
            System.out.println("You have gained +10 EXP!");
        } else if (monster.equals(monsters.get(1))){
            player.setExp(exp+20);
            System.out.println("You have gained +20 EXP!");
        }  else if (monster.equals(monsters.get(2))){
            player.setExp(exp+50);
            System.out.println("You have gained +50 EXP!");
        }  else if (monster.equals(monsters.get(3))){
            player.setExp(exp+100);
            System.out.println("You have gained +100 EXP!");
        }
    }


    //Monster Service

    public Monster randomMonster(Player player){
        Monster monsterKalashnikov = monsters.getFirst();
        Monster monsterAdamovich = monsters.get(1);
        Monster monsterKarelia = monsters.get(2);
        Monster monsterKrasnodar = monsters.get(3);

        int probability = (int)(Math.random() * 100);
        if (player.getRoomId() == 2){
            return monsters.getFirst();
        } else if (player.getRoomId() == 3){
            if (probability <= 85){
                return monsterKalashnikov;
            } else if (probability <= 100){
                return monsterAdamovich;
            }
        }  else if (player.getRoomId() == 4){
            if (probability <= 50){
                return monsterKalashnikov;
            } else if (probability <= 75){
                return monsterAdamovich;
            } else if (probability <= 100){
                return monsterKarelia;
            }
        } else if (player.getRoomId() == 5){
            if (probability <= 60){
                return monsterAdamovich;
            } else if (probability <= 100){
                return monsterKarelia;
            }
        } else if (player.getRoomId() == 6){
            if (probability <= 100){
                return monsterKrasnodar;
            }
        }
        return null;
    }

    public boolean monsterApparition(Monster monster, Player player) {
        boolean running = true;
        System.out.println("A monster has appeared!");
        System.out.println(player.getStats());
        System.out.println("----------------------------------------");
        System.out.println(monster.toString());
        while  (running) {
            System.out.println("What do you want to do?");
            System.out.println("1: Attack");
            System.out.println("2: Use item");
            System.out.println("3: Escape");
            if (Integer.parseInt(sc.nextLine()) == 1) {
                monster.takeDamage(player.getDmg());
                System.out.println(monster.toString());
                if (monster.getHP() <= 0) {
                    System.out.println("You win!");
                    addExp(monster, player);
                    nextLevel(player);
                    monster.resetMonsterHp();
                    running = false;
                } else  {
                    System.out.println("The monster strikes back!");
                    player.takeDamage(monster.getDmg());
                    if (player.getHP() <= 0) {
                        System.out.println("You have died");
                        System.out.println("All progress will be eliminated, too bad so sad!");
                        playerRepository.deletePlayer(player);
                        //I want to end the parent method here
                        return false;
                    }
                }
            } else if (Integer.parseInt(sc.nextLine()) == 2) {
                useItem(player);
            } else if (Integer.parseInt(sc.nextLine()) == 3) {
                System.out.println("You ran away");
                monster.resetMonsterHp();
                running = false;
            } else {
                System.out.println("Invalid choice");
            }
        }
        return true;
    }


    //Item Service
    public Item randomItem(){
        int probability = (int) (Math.random() * 100);
        if (probability < 50) {
            return items.getFirst();
        } else if (probability < 80) {
            return items.get(1);
        } else if (probability < 95) {
            return items.get(2);
        } else if (probability < 100) {
            return items.get(3);
        }
        return null;
    }

    public void itemApparition(Item item, Player player){
        System.out.println("You have found an item!");
        System.out.println(item.toStr());
        System.out.println("Do you want to add it to your inventory? Y/N");
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("N")) {
            System.out.println("You dropped the item");
            return;
        }
        System.out.println(item.getName() + " added to inventory!");
        player.getItems().add(item);
    }
}
