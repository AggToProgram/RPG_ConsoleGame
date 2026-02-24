package Rooms;

public class Room {
    private final String name, description;
    private final int requiredLvl;

    public Room(String name, String description, int requiredLvl) {
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

    @Override
    public String toString() {
        return name + "\n" + description;
    }
}
