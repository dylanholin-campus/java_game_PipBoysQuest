package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;
import serenadebird.pipboysquest.equipment.OffensiveEquipment;

/**
 * Classe abstraite representant un personnage du jeu.
 *
 * <p>Elle centralise les proprietes communes (nom, type, statistiques,
 * equipements, position sur le plateau) et les comportements de base.</p>
 */
public abstract class Character {
    private String type;
    private String name;
    private int healthLevel = 100;
    private int attackStrength = 10;
    private OffensiveEquipment offensiveEquipment;
    private DefensiveEquipment defensiveEquipment;
    private int boardPosition = 1;

    /**
     * Construit un personnage par defaut.
     */
    public Character() {
        this.type = "Unknown";
        this.name = "Unknown";
    }

    /**
     * Construit un personnage avec son type et son nom.
     *
     * @param type type du personnage (ex: Warrior, Wizard)
     * @param name nom du personnage
     */
    public Character(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Retourne l'action speciale associee a la classe concrete.
     *
     * @return nom de l'action speciale
     */
    public abstract String getSpecialAction();

    /**
     * Deplace le personnage sur le plateau.
     *
     * @param steps nombre de cases a avancer
     */
    public void move(int steps) {
        boardPosition += steps;
        System.out.println(name + " avance a la case " + boardPosition);
    }

    /**
     * Retire des points de vie au personnage.
     *
     * @param amount degats recus
     */
    public void takeDamage(int amount) {
        healthLevel -= amount;
        if (healthLevel < 0) {
            healthLevel = 0;
        }
    }

    /**
     * Indique si le personnage est encore en vie.
     *
     * @return true si les points de vie sont superieurs a zero
     */
    public boolean isAlive() {
        return healthLevel > 0;
    }

    /**
     * Retourne le type du personnage.
     *
     * @return type du personnage
     */
    public String getType() { return type; }

    /**
     * Met a jour le type du personnage.
     *
     * @param type nouveau type du personnage
     */
    public void setType(String type) { this.type = type; }

    /**
     * Retourne le nom du personnage.
     *
     * @return nom du personnage
     */
    public String getName() { return name; }

    /**
     * Met a jour le nom du personnage.
     *
     * @param name nouveau nom du personnage
     */
    public void setName(String name) { this.name = name; }

    /**
     * Retourne le niveau de vie actuel du personnage.
     *
     * @return niveau de vie actuel
     */
    public int getHealthLevel() { return healthLevel; }

    /**
     * Met a jour le niveau de vie du personnage.
     *
     * @param healthLevel nouveau niveau de vie
     */
    public void setHealthLevel(int healthLevel) { this.healthLevel = healthLevel; }

    /**
     * Retourne la force d'attaque du personnage.
     *
     * @return force d'attaque
     */
    public int getAttackStrength() { return attackStrength; }

    /**
     * Met a jour la force d'attaque du personnage.
     *
     * @param attackStrength nouvelle force d'attaque
     */
    public void setAttackStrength(int attackStrength) { this.attackStrength = attackStrength; }

    /**
     * Retourne l'equipement offensif actuellement equipe.
     *
     * @return equipement offensif equipe
     */
    public OffensiveEquipment getOffensiveEquipment() { return offensiveEquipment; }

    /**
     * Met a jour l'equipement offensif du personnage.
     *
     * @param offensiveEquipment nouvel equipement offensif
     */
    public void setOffensiveEquipment(OffensiveEquipment offensiveEquipment) { this.offensiveEquipment = offensiveEquipment; }

    /**
     * Retourne l'equipement defensif actuellement equipe.
     *
     * @return equipement defensif equipe
     */
    public DefensiveEquipment getDefensiveEquipment() { return defensiveEquipment; }

    /**
     * Met a jour l'equipement defensif du personnage.
     *
     * @param defensiveEquipment nouvel equipement defensif
     */
    public void setDefensiveEquipment(DefensiveEquipment defensiveEquipment) { this.defensiveEquipment = defensiveEquipment; }

    /**
     * Retourne la position actuelle du personnage sur le plateau.
     *
     * @return position actuelle sur le plateau
     */
    public int getBoardPosition() { return boardPosition; }

    /**
     * Met a jour la position du personnage sur le plateau.
     *
     * @param pos nouvelle position sur le plateau
     */
    public void setBoardPosition(int pos) { boardPosition = pos; }

    /**
     * Retourne un resume lisible du personnage.
     *
     * @return etat complet du personnage
     */
    @Override
    public String toString() {
        return "Personnage : " + name +
                ", Type : " + type +
                ", Niveau de vie : " + healthLevel +
                ", Force : " + attackStrength +
                ", Equipement offensif : " + offensiveEquipment +
                ", Equipement defensif : " + defensiveEquipment +
                ", Position : " + boardPosition;
    }
}
