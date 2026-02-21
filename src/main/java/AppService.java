import Entities.Monster;
import Entities.Player;
import Items.Item;
import Items.Potion;
import Items.Weapon;
import Rooms.Room;

import java.util.ArrayList;
import java.util.Scanner;

public class AppService {

    public static final Scanner sc = new Scanner(System.in);

    private static final ArrayList<Monster> monsters = new ArrayList<>();
    private static final ArrayList<Room> rooms = new ArrayList<>();

    Monster monsterKalashnikov = new Monster("Kalashnikov", 100, 10, "A weak monster used to being tormented", "Normal", 100);
    Monster monsterAdamovich = new Monster("Adamovich", 150, 35, "Kalashnikov's older brother, he is mad at you!", "Fire", 150);
    Monster monsterKarelia = new Monster("Karelia", 250, 50, "He will make sure you abandon this place as a ghost", "Ice", 250);
    Monster monsterKrasnodar = new Monster("Krasnodar", 500, 100, "The final boss, be careful, he is powerful!", "Thunder", 500);

    Item basicSword = new Weapon("Sword of the living", "Looks like a normal sword to me!", "sword", 0, 10);
    Item masterSword = new Weapon("Sword of the Undead", "A sword blessed by the heavenly Gods", "sword", 30, 10);
    Item healPotion = new Potion("Potion of healing", "A potion that heals", "Potion", "Healing", 50);
    Item ultraHealPotion = new Potion("Potion of the fallen angel", "Heals/adds 100hp", "Potion", "Healing", 100);

    Room room0 = new Room("Entrance of the dungeon", "The beginning of your adventure", 1, 0);
    Room room1 = new Room("The room of the living", "A greatly lit room full of statues of people, seemingly happy", 1, 1);
    Room room2 = new Room("The room of the martyrs", "A gently lit room, almost identical to the last one, " +
            "but the statues appear to be in pain", 3, 3);
    Room room3 = new Room("The room of the corpses", "A dark room whose only source of light being a candle. " +
            "The statues are lifeless and destroyed", 5, 3);
    Room room4 = new Room("The room of the spirits", "A fully dark room, the statues are gone, " +
            "but you are not alone", 7, 4);
    Room room5 = new Room("The room of the Hellborn", "The statues have returned in the form of demons. " +
            "They are watching you. They are watching you.", 12, 5);


    //Player Service
    public void playerExplore(Player player){

        if (player.getRoomId() == 0){
            System.out.println("Nothing to explore here");
            return;
        }

        int probability = (int)(Math.random() * 100);
        if (probability < 40) {
            monsterApparition(randomMonster(player), player);
        } else if (probability < 80) {
            itemApparition(randomItem(player), player);
        } else {
            System.out.println("Nothing has been found!");
        }
    }

    public void playerMove(Player player){
        System.out.println("Where do you wish to move?");
        System.out.println("1: Next room");
        System.out.println("2: Previous room");
        int direction = Integer.parseInt(sc.nextLine());

        int currentRoomId = player.getRoomId();
        int targetId;
        if (direction == 1){
            targetId = currentRoomId + 1;
        }  else if (direction == 2){
            targetId = currentRoomId - 1;
        } else {
            System.out.println("Invalid direction");
            return;
        }

        if (targetId < 0 || targetId >= rooms.size()) {
            System.out.println("You can't go that way.");
            return;
        }

        Room targetRoom = rooms.get(targetId);

        if (player.getLevel() < targetRoom.getRequiredLvl()) {
            System.out.println("You need to level up to level " + targetRoom.getRequiredLvl() + " to enter this room!");
            return;
        }

        player.setRoomId(targetId);
        System.out.println("Welcome to: ");
        System.out.println(targetRoom.toString());
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
        if (monster.equals(monsterKalashnikov)){
            player.setExp(exp+10);
            System.out.println("You have gained +10 EXP!");
        } else if (monster.equals(monsterAdamovich)){
            player.setExp(exp+20);
            System.out.println("You have gained +20 EXP!");
        }  else if (monster.equals(monsterKarelia)){
            player.setExp(exp+50);
            System.out.println("You have gained +50 EXP!");
        }  else if (monster.equals(monsterKrasnodar)){
            player.setExp(exp+100);
            System.out.println("You have gained +100 EXP!");
        }
    }


    //Monster Service
    public void addMonsters(){
        monsters.add(monsterKalashnikov);
        monsters.add(monsterAdamovich);
        monsters.add(monsterKarelia);
        monsters.add(monsterKrasnodar);
    }

    public Monster randomMonster(Player player){
        int probability = (int)(Math.random() * 100);
        if (player.getRoomId() == 1){
            return monsterKalashnikov;
        } else if (player.getRoomId() == 2){
            if (probability <= 85){
                return monsterKalashnikov;
            } else if (probability <= 100){
                return monsterAdamovich;
            }
        }  else if (player.getRoomId() == 3){
            if (probability <= 50){
                return monsterKalashnikov;
            } else if (probability <= 75){
                return monsterAdamovich;
            } else if (probability <= 100){
                return monsterKarelia;
            }
        } else if (player.getRoomId() == 4){
            if (probability <= 60){
                return monsterAdamovich;
            } else if (probability <= 100){
                return monsterKarelia;
            }
        } else if (player.getRoomId() == 5){
            if (probability <= 100){
                return monsterKrasnodar;
            }
        }
        return null;
    }

    public void monsterApparition(Monster monster, Player player){
        boolean running = true;
        System.out.println("A monster has appeared!");
        System.out.println(player.getStats());
        System.out.println("----------------------------------------");
        System.out.println(monster.toString());
        while  (running) {
            System.out.println("\rDo you wish to attack? Y/N");
            if (sc.nextLine().equalsIgnoreCase("Y")) {
                monster.takeDamage(player.getDmg());
                System.out.println("\r" + monster.toString());
                if (monster.getHP() <= 0) {
                    System.out.println("You win!");
                    addExp(monster, player);
                    nextLevel(player);
                    monster.resetMonsterHp();
                    running = false;
                } else  {
                    System.out.println("The monster strikes back!");
                    player.takeDamage(monster.getDmg());
                }
            } else {
                System.out.println("He ran away!");
                running = false;
            }
        }
    }


    //Room Service?
    public void addRooms(){
        AppService.rooms.add(room0);
        AppService.rooms.add(room1);
        AppService.rooms.add(room2);
        AppService.rooms.add(room3);
        AppService.rooms.add(room4);
        AppService.rooms.add(room5);
    }


    //Item Service
    public Item randomItem(Player player){
        int probability = (int) (Math.random() * 100);
        if (probability < 50) {
            return basicSword;
        } else if (probability < 80) {
            return healPotion;
        } else if (probability < 95) {
            return ultraHealPotion;
        } else if (probability < 100) {
            return masterSword;
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
