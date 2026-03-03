package serenadebird.pipboysquest.equipment.offensive;

import serenadebird.pipboysquest.equipment.OffensiveEquipment;

/**
 * Equipement offensif de type arme.
 */
public class Weapon extends OffensiveEquipment {
    /**
     * Construit une arme par defaut.
     */
    public Weapon() {
        super("Weapon", "Basic Weapon", 1);
    }

    /**
     * Construit une arme personnalisee.
     *
     * @param name nom de l'arme
     * @param attackLevel valeur d'attaque
     */
    public Weapon(String name, int attackLevel) {
        super("Weapon", name, attackLevel);
    }

    /**
     * Retourne une description lisible de l'arme.
     *
     * @return description de l'arme
     */
    @Override
    public String toString() {
        return "Arme " + super.toString();
    }
}
