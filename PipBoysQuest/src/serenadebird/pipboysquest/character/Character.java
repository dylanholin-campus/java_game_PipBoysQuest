package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;
import serenadebird.pipboysquest.equipment.OffensiveEquipment;

public abstract class Character {
    private String type;
    private String name;
    private int healthLevel = 100;
    private int attackStrength = 10;
    private OffensiveEquipment offensiveEquipment;
    private DefensiveEquipment defensiveEquipment;
    private int boardPosition = 1;

    public Character() {
        this.type = "Unknown";
        this.name = "Unknown";
    }

    public Character(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public void move(int steps) {
        boardPosition += steps;
        System.out.println(name + " avance a la case " + boardPosition);
    }

    public void takeDamage(int amount) {
        healthLevel -= amount;
        if (healthLevel < 0) {
            healthLevel = 0;
        }
    }

    public boolean isAlive() {
        return healthLevel > 0;
    }

    // Getters/Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHealthLevel() { return healthLevel; }
    public void setHealthLevel(int healthLevel) { this.healthLevel = healthLevel; }

    public int getAttackStrength() { return attackStrength; }
    public void setAttackStrength(int attackStrength) { this.attackStrength = attackStrength; }

    public OffensiveEquipment getOffensiveEquipment() { return offensiveEquipment; }
    public void setOffensiveEquipment(OffensiveEquipment offensiveEquipment) { this.offensiveEquipment = offensiveEquipment; }

    public DefensiveEquipment getDefensiveEquipment() { return defensiveEquipment; }
    public void setDefensiveEquipment(DefensiveEquipment defensiveEquipment) { this.defensiveEquipment = defensiveEquipment; }

    public int getBoardPosition() { return boardPosition; }
    public void setBoardPosition(int pos) { boardPosition = pos; }

    @Override
    public String toString() {
        return "Character{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", healthLevel=" + healthLevel +
                ", attackStrength=" + attackStrength +
                ", offensiveEquipment=" + offensiveEquipment +
                ", defensiveEquipment=" + defensiveEquipment +
                ", boardPosition=" + boardPosition +
                '}';
    }
}
