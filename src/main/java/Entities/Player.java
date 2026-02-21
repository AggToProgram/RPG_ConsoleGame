package Entities;

import Items.Item;
import Items.Weapon;

import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Entity{
    private int mana;
    private int gold;
    private int level;
    private int roomId;
    private int exp;

    private final ArrayList<Item> items = new ArrayList<>();

    public Player(String name, int hp, int dmg, String description, int mana, int gold, int level, int roomId, int exp) {
        super(name, hp, dmg, description);
        this.mana = mana;
        this.gold = gold;
        this.level = level;
        this.roomId = roomId;
        this.exp = exp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getStats(){
        return "- Your hp: " + this.getHP() + "\n- Current damage: " + this.getDmg() +
                "\n- Your mana: " + this.getMana() + "\n- Current room: " + this.getRoomId() + "\n- Level: " + this.getLevel() + "\n- Exp: " + this.getExp();
    }

    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }

    public ArrayList<Item> getItems() {
        return items;
    }


    public void useItem(Player player){
        if(items.isEmpty()){
            System.out.println("No items in your hand");
            return;
        }

        for (Item item : items) {
            System.out.println(item.toStr());
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("What item would you like to use? Type 0 to cancel.");
        String itemName = sc.nextLine();

        if(itemName.equals("0")){
            System.out.println("No items used");
            return;
        }

        Item foundItem = findItem(itemName);
        foundItem.itemEffect(player);
        items.remove(foundItem);

    }

    public Item findItem(String itemName){
        for (Item item : items) {
            if(item.getName().equalsIgnoreCase(itemName)){
                return item;
            }
        }
        return null;
    }


}
