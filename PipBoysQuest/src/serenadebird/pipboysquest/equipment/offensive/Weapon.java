package serenadebird.pipboysquest.equipment.offensive;

import serenadebird.pipboysquest.equipment.OffensiveEquipment;

public class Weapon extends OffensiveEquipment {
    public Weapon() {
        super("Weapon", "Basic Weapon", 1);
    }

    public Weapon(String name, int attackLevel) {
        super("Weapon", name, attackLevel);
    }

    @Override
    public String toString() {
        return "Arme " + super.toString();
    }
}

