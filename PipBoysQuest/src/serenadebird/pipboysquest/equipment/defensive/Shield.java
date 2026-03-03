package serenadebird.pipboysquest.equipment.defensive;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;

public class Shield extends DefensiveEquipment {
    public Shield() {
        super("Shield", "Basic Shield", 1);
    }

    public Shield(String name, int defenseLevel) {
        super("Shield", name, defenseLevel);
    }

    @Override
    public String toString() {
        return "Bouclier " + super.toString();
    }
}

