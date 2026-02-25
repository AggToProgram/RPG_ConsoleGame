package Entities;

public abstract class Entity {
    private int id;
    private final String name;
    private int hp;
    private int dmg;
    private final String description;

    public Entity(String name, int hp, int dmg, String description) {
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.description = description;
    }

    public Entity(int id, String name, int hp, int dmg, String description) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHP() {
        return hp;
    }
    public void setHP(int HP) {
        this.hp = HP;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public String toString() {
        return "Name: " + name + "\nHP: " + hp + "\nDMG: " + dmg + "\nDescription: " + description;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
