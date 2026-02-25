package Items;

public class Potion extends Item {
    private final String buffType;

    public Potion(int id, String name, String description, String type, int dmg, String buffType) {
        super(id, name, description, type, dmg);
        this.buffType = buffType;
    }

    public String getBuffType() {
        return buffType;
    }

    @Override
    public String toStr() {
        return getName() + "\n- " + getDescription() + "\n- Type: " + getType() + "\n- Buff type: " + getBuffType() + "\n- Healed points: " + getDmg() + "\n";
    }
}
