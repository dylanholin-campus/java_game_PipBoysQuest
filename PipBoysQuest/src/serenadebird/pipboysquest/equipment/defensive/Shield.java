package serenadebird.pipboysquest.equipment.defensive;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;

/**
 * Equipement defensif de type bouclier.
 */
public class Shield extends DefensiveEquipment {
    /**
     * Construit un bouclier personnalise.
     *
     * @param name nom du bouclier
     * @param defenseLevel valeur de defense
     */
    // Permet de creer un bouclier specifique pour un loot ou un preset de personnage.
    public Shield(String name, int defenseLevel) {
        // Conserve le type metier "Shield" et applique les valeurs fournies.
        super("Shield", name, defenseLevel);
    }
}
