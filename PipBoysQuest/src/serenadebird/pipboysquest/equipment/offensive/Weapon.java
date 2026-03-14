package serenadebird.pipboysquest.equipment.offensive;

import serenadebird.pipboysquest.equipment.OffensiveEquipment;

/**
 * Equipement offensif de type arme.
 */
public class Weapon extends OffensiveEquipment {
    /**
     * Construit une arme par defaut.
     */
    // Initialise une arme basique avec un bonus minimal.
    public Weapon() {
        // Type metier fixe + valeurs de depart.
        super("Weapon", "Basic Weapon", 1);
    }

    /**
     * Construit une arme personnalisee.
     *
     * @param name nom de l'arme
     * @param attackLevel valeur d'attaque
     */
    // Permet de definir une arme personnalisee (loot, equipement initial, etc.).
    public Weapon(String name, int attackLevel) {
        // Conserve le type "Weapon" et applique les parametres fournis.
        super("Weapon", name, attackLevel);
    }

    /**
     * Retourne une description lisible de l'arme.
     *
     * @return description de l'arme
     */
    @Override
    // Prefixe le rendu parent pour expliciter la famille d'equipement.
    public String toString() {
        return "Arme " + super.toString();
    }
}
