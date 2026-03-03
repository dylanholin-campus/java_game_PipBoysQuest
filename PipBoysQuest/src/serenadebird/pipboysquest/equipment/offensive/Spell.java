package serenadebird.pipboysquest.equipment.offensive;

import serenadebird.pipboysquest.equipment.OffensiveEquipment;

/**
 * Equipement offensif de type sort.
 */
public class Spell extends OffensiveEquipment {
    /**
     * Construit un sort par defaut.
     */
    public Spell() {
        super("Spell", "Basic Spell", 1);
    }

    /**
     * Construit un sort personnalise.
     *
     * @param name nom du sort
     * @param attackLevel valeur d'attaque
     */
    public Spell(String name, int attackLevel) {
        super("Spell", name, attackLevel);
    }

    /**
     * Retourne une description lisible du sort.
     *
     * @return description du sort
     */
    @Override
    public String toString() {
        return "Sort " + super.toString();
    }
}
