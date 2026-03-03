package serenadebird.pipboysquest.equipment;

/**
 * Classe abstraite de base pour les equipements offensifs.
 *
 * <p>Les classes concretes (ex: Weapon, Spell) definissent le type exact
 * d'equipement attaque.</p>
 */
public abstract class OffensiveEquipment {
    private String type;
    private int attackLevel;
    private String name;

    /**
     * Construit un equipement offensif par defaut.
     */
    public OffensiveEquipment() {
        this.type = "Unknown";
        this.name = "Unknown";
        this.attackLevel = 0;
    }

    /**
     * Construit un equipement offensif avec ses attributs.
     *
     * @param type type d'equipement (Weapon, Spell)
     * @param name nom de l'equipement
     * @param attackLevel bonus d'attaque
     */
    public OffensiveEquipment(String type, String name, int attackLevel) {
        this.type = type;
        this.name = name;
        this.attackLevel = attackLevel;
    }

    /**
     * Retourne le type de l'equipement offensif.
     *
     * @return type de l'equipement
     */
    public String getType() { return type; }

    /**
     * Met a jour le type de l'equipement offensif.
     *
     * @param type nouveau type d'equipement
     */
    public void setType(String type) { this.type = type; }

    /**
     * Retourne la valeur d'attaque de l'equipement.
     *
     * @return niveau d'attaque de l'equipement
     */
    public int getAttackLevel() { return attackLevel; }

    /**
     * Met a jour la valeur d'attaque de l'equipement.
     *
     * @param attackLevel nouveau bonus d'attaque
     */
    public void setAttackLevel(int attackLevel) { this.attackLevel = attackLevel; }

    /**
     * Retourne le nom de l'equipement offensif.
     *
     * @return nom de l'equipement
     */
    public String getName() { return name; }

    /**
     * Met a jour le nom de l'equipement offensif.
     *
     * @param name nouveau nom de l'equipement
     */
    public void setName(String name) { this.name = name; }

    /**
     * Retourne une description lisible de l'equipement.
     *
     * @return description formatee
     */
    @Override
    public String toString() {
        return type + " '" + name + "' (attaque +" + attackLevel + ")";
    }
}
