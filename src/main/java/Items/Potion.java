package Items;

public class Potion extends Item {
    private String buffType;

    public Potion(String name, String description, String type, String buffType, int dmg) {
        super(name, description, type, dmg);
        this.buffType = buffType;
    }

    public String getBuffType() {
        return buffType;
    }

    public void setBuffType(String buffType) {
        this.buffType = buffType;
    }

    @Override
    public String toStr() {
        return getName() + "\n- " + getDescription() + "\n- Type: " + getType() + "\n- Buff type: " + getBuffType() + "\n- Healed points: " + getDmg() + "\n";
    }
}
