package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;
import serenadebird.pipboysquest.equipment.OffensiveEquipment;

public class Wizard extends Character {
    public Wizard(String name) {
        super("Wizard", name);
        setHealthLevel(100);
        setAttackStrength(12);
        setOffensiveEquipment(new OffensiveEquipment("Spell", "Gamma ray", 6));
        setDefensiveEquipment(new DefensiveEquipment("Potion", "Stimpak", 4));
    }

    @Override
    public String toString() {
        return "Wizard{" +
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
