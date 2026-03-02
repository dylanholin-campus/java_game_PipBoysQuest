package serenadebird.pipboysquest.equipment;

public class OffensiveEquipment {
    private String type;
    private int attackLevel;
    private String name;

    public OffensiveEquipment() {
        this.type = "Unknown";
        this.name = "Unknown";
        this.attackLevel = 0;
    }

    public OffensiveEquipment(String type, String name, int attackLevel) {
        this.type = type;
        this.name = name;
        this.attackLevel = attackLevel;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getAttackLevel() { return attackLevel; }
    public void setAttackLevel(int attackLevel) { this.attackLevel = attackLevel; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "OffensiveEquipment{" +
                "type='" + type + '\'' +
                ", attackLevel=" + attackLevel +
                ", name='" + name + '\'' +
                '}';
    }
}

