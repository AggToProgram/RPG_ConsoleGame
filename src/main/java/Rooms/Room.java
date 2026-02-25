package Rooms;

public class Room {
    private final String name, description;
    private final int requiredLvl, id;

    public Room(int id, String name, String description, int requiredLvl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredLvl = requiredLvl;
    }
    public String getName() {
        return name;
    }
    public int getRequiredLvl() {
        return requiredLvl;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + "\n" + description;
    }
}
