package Entities;

public class Monster extends Entity{
    private final String type;
    private final int ogHp;

    public Monster(int id, String name, int hp, int dmg, String description, String type, int ogHp) {
        super(id, name, hp, dmg, description);
        this.type = type;
        this.ogHp = ogHp;
    }

    public int getOgHp() {
        return ogHp;
    }

    public void resetMonsterHp(){
        int ogHp = getOgHp();
        setHP(ogHp);
    }
}
