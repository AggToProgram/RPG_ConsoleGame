package Entities;

public abstract class Entity {
    private String name;
    private int hp;
    private int dmg;
    private String description;

    public Entity(String name, int hp, int dmg, String description) {
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public String toString() {
        return "Name: " + name + "\nHP: " + hp + "\nDMG: " + dmg + "\nDescription: " + description;
    }
}
