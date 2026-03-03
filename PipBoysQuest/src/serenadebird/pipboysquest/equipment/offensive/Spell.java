package serenadebird.pipboysquest.equipment.offensive;

import serenadebird.pipboysquest.equipment.OffensiveEquipment;

public class Spell extends OffensiveEquipment {
    public Spell() {
        super("Spell", "Basic Spell", 1);
    }

    public Spell(String name, int attackLevel) {
        super("Spell", name, attackLevel);
    }

    @Override
    public String toString() {
        return "Sort " + super.toString();
    }
}

