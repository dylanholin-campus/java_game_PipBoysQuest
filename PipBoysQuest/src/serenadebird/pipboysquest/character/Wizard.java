package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.defensive.Potion;
import serenadebird.pipboysquest.equipment.offensive.Weapon;

/**
 * Specialisation de Character pour un personnage oriente magie.
 */
public class Wizard extends Character {
    /**
     * Construit un magicien avec ses statistiques et equipements par defaut.
     *
     * @param name nom du personnage
     */
    public Wizard(String name) {
        // Definit le type metier et le nom.
        super("Wizard", name);
        // Profil de base: un peu moins de vie, orientation attaques magiques.
        setHealthLevel(100);
        setAttackStrength(12);
        // Equipement de depart offensif.
        setOffensiveEquipment(new Weapon("Pistolet gamma", 5));
        // Equipement de depart defensif.
        setDefensiveEquipment(new Potion("Stimpack", 20));
    }

    /**
     * Retourne l'action speciale du magicien.
     *
     * @return nom de l'action speciale
     */
    @Override
    public String getSpecialAction() {
        // Action signature de la classe Wizard.
        return "Concentration arcanique";
    }

    /**
     * Retourne une description complete du magicien.
     *
     * @return description du personnage
     */
    @Override
    public String toString() {
        // Reutilise le resume parent puis ajoute la competence speciale.
        return super.toString() + ", Action speciale : " + getSpecialAction();
    }
}
