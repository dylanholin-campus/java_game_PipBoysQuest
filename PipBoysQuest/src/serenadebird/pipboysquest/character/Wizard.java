package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.defensive.Potion;
import serenadebird.pipboysquest.equipment.offensive.Spell;

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
        super("Wizard", name);
        setHealthLevel(100);
        setAttackStrength(12);
        setOffensiveEquipment(new Spell("Gamma Ray", 6));
        setDefensiveEquipment(new Potion("Stimpak", 4));
    }

    /**
     * Retourne l'action speciale du magicien.
     *
     * @return nom de l'action speciale
     */
    @Override
    public String getSpecialAction() {
        return "Concentration arcanique";
    }

    /**
     * Retourne une description complete du magicien.
     *
     * @return description du personnage
     */
    @Override
    public String toString() {
        return super.toString() + ", Action speciale : " + getSpecialAction();
    }
}
