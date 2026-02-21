package Rooms;

public class Room {
    private String name, description;
    private int requiredLvl, id;

    public Room(String name, String description, int requiredLvl, int id) {
        this.name = name;
        this.description = description;
        this.requiredLvl = requiredLvl;
        this.id = id;
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
    public int getRequiredLvl() {
        return requiredLvl;
    }
    public void setRequiredLvl(int requiredLvl) {
        this.requiredLvl = requiredLvl;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + "\n" + description;
    }
}
