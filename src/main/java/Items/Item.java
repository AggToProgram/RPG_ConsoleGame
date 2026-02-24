package Items;

import Entities.Player;

public abstract class Item {
    private final String name, description, type;
    private final int dmg;

    public Item(String name, String description, String type,  int dmg) {
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

    public void itemEffect(Player player) {
        if (getType().equalsIgnoreCase("sword")){
            player.setDmg(player.getDmg() + getDmg());
        } else if (getType().equalsIgnoreCase("potion")){
            player.setHP(player.getHP() + getDmg());
        }
    }
}
