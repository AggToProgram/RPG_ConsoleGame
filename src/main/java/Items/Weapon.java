package Items;

public class Weapon extends Item{
    private int manaDmg;

    public Weapon(String name, String description, String type, int manaDmg, int dmg) {
        super(name, description, type, dmg);
        this.manaDmg = manaDmg;
    }

    public int getManaDmg() {
        return manaDmg;
    }

    public void setManaDmg(int manaDmg) {
        this.manaDmg = manaDmg;
    }

    @Override
    public String toStr() {
        return getName() + ":" + "\n- " + getDescription() +  "\n- Type: " + getType() + "\n- Damage: " + getDmg() + "\n- Mana Damage: " + getManaDmg() + "\n";
    }
}
