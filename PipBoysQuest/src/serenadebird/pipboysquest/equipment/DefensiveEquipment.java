package serenadebird.pipboysquest.equipment;

/**
 * Classe abstraite de base pour les equipements defensifs.
 *
 * <p>Les classes concretes (ex: Shield, Potion) specialisent le type de
 * protection applique au personnage.</p>
 */
public abstract class DefensiveEquipment {
    private String type;
    private int defenseLevel;
    private String name;

    /**
     * Construit un equipement defensif par defaut.
     */
    public DefensiveEquipment() {
        this.type = "Unknown";
        this.name = "Unknown";
        this.defenseLevel = 0;
    }

    /**
     * Construit un equipement defensif avec ses attributs.
     *
     * @param type type d'equipement (Shield, Potion)
     * @param name nom de l'equipement
     * @param defenseLevel bonus de defense
     */
    public DefensiveEquipment(String type, String name, int defenseLevel) {
        this.type = type;
        this.name = name;
        this.defenseLevel = defenseLevel;
    }

    /**
     * Retourne le type de l'equipement defensif.
     *
     * @return type de l'equipement
     */
    public String getType() { return type; }

    /**
     * Met a jour le type de l'equipement defensif.
     *
     * @param type nouveau type d'equipement
     */
    public void setType(String type) { this.type = type; }

    /**
     * Retourne la valeur de defense de l'equipement.
     *
     * @return niveau de defense de l'equipement
     */
    public int getDefenseLevel() { return defenseLevel; }

    /**
     * Met a jour la valeur de defense de l'equipement.
     *
     * @param defenseLevel nouveau bonus de defense
     */
    public void setDefenseLevel(int defenseLevel) { this.defenseLevel = defenseLevel; }

    /**
     * Retourne le nom de l'equipement defensif.
     *
     * @return nom de l'equipement
     */
    public String getName() { return name; }

    /**
     * Met a jour le nom de l'equipement defensif.
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
        return type + " '" + name + "' (defense +" + defenseLevel + ")";
    }
}
