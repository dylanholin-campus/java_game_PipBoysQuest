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
        // Definit le type metier et le nom.
        super("Warrior", name);
        // Profil de base: plus de vie et de force que le wizard.
        setHealthLevel(120);
        // Equipement de depart offensif.
        setOffensiveEquipment(new Weapon("Fusil laser", 7));
        // La force affichée inclut toujours l'equipement offensif courant.
        setAttackStrength(computeAttackWithEquipment(getOffensiveEquipment()));
        // Equipement de depart defensif.
        setDefensiveEquipment(new Shield("Power Armor Shield", 10));
    }

    /**
     * Retourne l'action speciale du guerrier.
     *
     * @return nom de l'action speciale
     */
    @Override
    public String getSpecialAction() {
        // Action signature de la classe Warrior.
        return "Charge brutale";
    }

    /**
     * Retourne une description complete du guerrier.
     *
     * @return description du personnage
     */
    @Override
    public String toString() {
        // Reutilise le resume parent puis ajoute la competence speciale.
        return super.toString() + ", Action speciale : " + getSpecialAction();
    }
}
