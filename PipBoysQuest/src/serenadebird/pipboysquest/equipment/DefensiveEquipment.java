package serenadebird.pipboysquest.equipment;

/**
 * Classe abstraite de base pour les equipements defensifs.
 *
 * <p>Les classes concretes (ex: Shield, Potion) specialisent le type de
 * protection applique au personnage.</p>
 */
public abstract class DefensiveEquipment {
    // Type metier de l'equipement (ex: Shield, Potion).
    private String type;
    // Bonus defensif apporte par l'equipement.
    private int defenseLevel;
    // Nom lisible affiche au joueur.
    private String name;

    /**
     * Construit un equipement defensif par defaut.
     */
    // Initialise un objet neutre pour eviter un etat incomplet.
    public DefensiveEquipment() {
        // Valeurs de secours en l'absence de donnees metier.
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
    // Constructeur principal utilise par les classes filles concretes.
    public DefensiveEquipment(String type, String name, int defenseLevel) {
        // Affecte les proprietes de base de l'equipement defensif.
        this.type = type;
        this.name = name;
        this.defenseLevel = defenseLevel;
    }

    /**
     * Retourne le type de l'equipement defensif.
     *
     * @return type de l'equipement
     */
    // Accesseur du type metier.
    public String getType() { return type; }

    /**
     * Met a jour le type de l'equipement defensif.
     *
     * @param type nouveau type d'equipement
     */
    // Mutateur du type metier.
    public void setType(String type) { this.type = type; }

    /**
     * Retourne la valeur de defense de l'equipement.
     *
     * @return niveau de defense de l'equipement
     */
    // Accesseur du bonus defensif.
    public int getDefenseLevel() { return defenseLevel; }

    /**
     * Met a jour la valeur de defense de l'equipement.
     *
     * @param defenseLevel nouveau bonus de defense
     */
    // Mutateur du bonus defensif.
    public void setDefenseLevel(int defenseLevel) { this.defenseLevel = defenseLevel; }

    /**
     * Retourne le nom de l'equipement defensif.
     *
     * @return nom de l'equipement
     */
    // Accesseur du nom lisible.
    public String getName() { return name; }

    /**
     * Met a jour le nom de l'equipement defensif.
     *
     * @param name nouveau nom de l'equipement
     */
    // Mutateur du nom lisible.
    public void setName(String name) { this.name = name; }

    /**
     * Retourne une description lisible de l'equipement.
     *
     * @return description formatee
     */
    @Override
    // Format standard reutilise dans les logs, inventaire et descriptions de case.
    public String toString() {
        return type + " '" + name + "' (defense +" + defenseLevel + ")";
    }
}
