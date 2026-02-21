package Items;

import Entities.Player;

public abstract class Item {
    private String name, description, type;
    private int dmg;

    public Item(String name, String description, String type,  int dmg) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.dmg = dmg;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public String toStr() {
        return null;
    }

    public void itemEffect(Player player) {
        if (getType().equalsIgnoreCase("sword")){
            player.setDmg(player.getDmg() + getDmg());
        } else if (getType().equalsIgnoreCase("potion")){
            player.setHP(player.getHP() + getDmg());
        }
    }
}
