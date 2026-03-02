package serenadebird.pipboysquest.equipment;

public class DefensiveEquipment {
    private String type;
    private int defenseLevel;
    private String name;

    public DefensiveEquipment() {
        this.type = "Unknown";
        this.name = "Unknown";
        this.defenseLevel = 0;
    }

    public DefensiveEquipment(String type, String name, int defenseLevel) {
        this.type = type;
        this.name = name;
        this.defenseLevel = defenseLevel;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getDefenseLevel() { return defenseLevel; }
    public void setDefenseLevel(int defenseLevel) { this.defenseLevel = defenseLevel; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "DefensiveEquipment{" +
                "type='" + type + '\'' +
                ", defenseLevel=" + defenseLevel +
                ", name='" + name + '\'' +
                '}';
    }
}

