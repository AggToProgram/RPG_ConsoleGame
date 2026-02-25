package Items;

import Entities.Player;

public class Item {
    private final String name, description, type;
    private final int dmg;
    private final int id;

    public Item(int id, String name, String description, String type,  int dmg) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.dmg = dmg;
    }

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public int getDmg() {
        return dmg;
    }

    public String toStr() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void itemEffect(Player player) {
        if (getType().equalsIgnoreCase("sword")){
            player.setDmg(player.getDmg() + getDmg());
        } else if (getType().equalsIgnoreCase("potion")){
            player.setHP(player.getHP() + getDmg());
        }
    }
}
