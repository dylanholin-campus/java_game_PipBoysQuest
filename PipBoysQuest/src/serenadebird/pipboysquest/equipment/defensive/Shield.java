package serenadebird.pipboysquest.equipment.defensive;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;

/**
 * Equipement defensif de type bouclier.
 */
public class Shield extends DefensiveEquipment {
    /**
     * Construit un bouclier par defaut.
     */
    public Shield() {
        super("Shield", "Basic Shield", 1);
    }

    /**
     * Construit un bouclier personnalise.
     *
     * @param name nom du bouclier
     * @param defenseLevel valeur de defense
     */
    public Shield(String name, int defenseLevel) {
        super("Shield", name, defenseLevel);
    }

    /**
     * Retourne une description lisible du bouclier.
     *
     * @return description du bouclier
     */
    @Override
    public String toString() {
        return "Bouclier " + super.toString();
    }
}
