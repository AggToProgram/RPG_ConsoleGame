import Entities.Player;

import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        AppService appService = new AppService();

        System.out.println("Traveller, what is your name?");
        String name = sc.nextLine();
        System.out.println("Welcome " + name + ", tell us about yourself");
        String description = sc.nextLine();
        int roomId = 0;
        Player player = new Player(name, 100, 25, description, 200, 0, 1, roomId, 0);

        appService.addMonsters();
        appService.addRooms();

        System.out.println("You have entered a dungeon, the exit is blocked, you are clearly trapped.");
        System.out.println("You are in a room with some statues that look abstract.");
        System.out.println(appService.room0.toString());

        boolean running = true;
        while(running){
            System.out.println("What do you wish to do?");
            System.out.println("1: Move");
            System.out.println("2: Explore");
            System.out.println("3: Use item");
            System.out.println("4: Your stats");
            System.out.println("5: Exit game");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> appService.playerMove(player);
                case 2 -> appService.playerExplore(player);
                case 3 -> appService.useItem(player);
                case 4 -> System.out.println(player.getStats());
                case 5 -> running = false;
            }
        }
    }
}
