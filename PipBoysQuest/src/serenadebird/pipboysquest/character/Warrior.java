package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.defensive.Shield;
import serenadebird.pipboysquest.equipment.offensive.Weapon;

/**
 * Specialisation de Character pour un combattant oriente force.
 */
public class Warrior extends Character {
    /**
     * Construit un guerrier avec ses statistiques et equipements par defaut.
     *
     * @param name nom du personnage
     */
    public Warrior(String name) {
        super("Warrior", name);
        setHealthLevel(120);
        setAttackStrength(15);
        setOffensiveEquipment(new Weapon("Laser Rifle", 5));
        setDefensiveEquipment(new Shield("Power Armor Shield", 10));
    }

    /**
     * Retourne l'action speciale du guerrier.
     *
     * @return nom de l'action speciale
     */
    @Override
    public String getSpecialAction() {
        return "Charge brutale";
    }

    /**
     * Retourne une description complete du guerrier.
     *
     * @return description du personnage
     */
    @Override
    public String toString() {
        return super.toString() + ", Action speciale : " + getSpecialAction();
    }
}
