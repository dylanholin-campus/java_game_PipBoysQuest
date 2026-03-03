package serenadebird.pipboysquest.equipment.defensive;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;

public class Potion extends DefensiveEquipment {
    public Potion() {
        super("Potion", "Basic Potion", 1);
    }

    public Potion(String name, int defenseLevel) {
        super("Potion", name, defenseLevel);
    }

    @Override
    public String toString() {
        return "Potion " + super.toString();
    }
}

