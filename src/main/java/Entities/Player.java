package Entities;

import Items.Item;

import java.util.ArrayList;

public class Player extends Entity{
    private int mana;
    private int gold;
    private int level;
    private int roomId;
    private int exp;

    private final ArrayList<Item> items = new ArrayList<>();

    public Player(int id, String name, int hp, int dmg, String description, int mana, int gold, int level, int roomId, int exp) {
        super(id, name, hp, dmg, description);
        this.mana = mana;
        this.gold = gold;
        this.level = level;
        this.roomId = roomId;
        this.exp = exp;
    }

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
}
