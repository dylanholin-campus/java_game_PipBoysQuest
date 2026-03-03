package serenadebird.pipboysquest.equipment.defensive;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;

/**
 * Equipement defensif de type potion.
 */
public class Potion extends DefensiveEquipment {
    /**
     * Construit une potion par defaut.
     */
    public Potion() {
        super("Potion", "Basic Potion", 1);
    }

    /**
     * Construit une potion personnalisee.
     *
     * @param name nom de la potion
     * @param defenseLevel valeur de defense
     */
    public Potion(String name, int defenseLevel) {
        super("Potion", name, defenseLevel);
    }

    /**
     * Retourne une description lisible de la potion.
     *
     * @return description de la potion
     */
    @Override
    public String toString() {
        return "Potion " + super.toString();
    }
}
