package Entities;

public class Monster extends Entity{
    private String type;
    private final int ogHp;

    public Monster(String name, int hp, int dmg, String description, String type, int ogHp) {
        super(name, hp, dmg, description);
        this.type = type;
        this.ogHp = ogHp;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getOgHp() {
        return ogHp;
    }

    public void resetMonsterHp(){
        int ogHp = getOgHp();
        setHP(ogHp);
    }
}
