package serenadebird.pipboysquest.equipment.defensive;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;

/**
 * Equipement defensif de type potion.
 */
public class Potion extends DefensiveEquipment {
    /**
     * Construit une potion par defaut.
     */
    // Initialise une potion basique avec un faible bonus defensif.
    public Potion() {
        // Type = Potion, nom lisible, bonus defense de base.
        super("Potion", "Basic Potion", 1);
    }

    /**
     * Construit une potion personnalisee.
     *
     * @param name nom de la potion
     * @param defenseLevel valeur de defense
     */
    // Permet de creer une potion specifique (loot, equipement de depart, etc.).
    public Potion(String name, int defenseLevel) {
        // Conserve le type metier "Potion" et injecte les valeurs personnalisees.
        super("Potion", name, defenseLevel);
    }

    /**
     * Retourne une description lisible de la potion.
     *
     * @return description de la potion
     */
    @Override
    // Prefixe la representation standard heritee pour un affichage plus explicite.
    public String toString() {
        return super.toString();
    }
}
