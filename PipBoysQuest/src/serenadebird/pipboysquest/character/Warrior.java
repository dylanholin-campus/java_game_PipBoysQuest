package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;
import serenadebird.pipboysquest.equipment.OffensiveEquipment;

public class Warrior extends Character {
    public Warrior(String name) {
        super("Warrior", name);
        setHealthLevel(120);
        setAttackStrength(15);
        setOffensiveEquipment(new OffensiveEquipment("Weapon", "Laser rifle", 5));
        setDefensiveEquipment(new DefensiveEquipment("Shield", "Power armor", 6));
    }

    @Override
    public String toString() {
        return "Warrior{" +
                "type='" + getType() + '\'' +
                ", name='" + getName() + '\'' +
                ", healthLevel=" + getHealthLevel() +
                ", attackStrength=" + getAttackStrength() +
                ", offensiveEquipment=" + getOffensiveEquipment() +
                ", defensiveEquipment=" + getDefensiveEquipment() +
                ", boardPosition=" + getBoardPosition() +
                '}';
    }
}
