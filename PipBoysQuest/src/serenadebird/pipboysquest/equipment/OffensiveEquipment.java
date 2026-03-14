package serenadebird.pipboysquest.equipment;

/**
 * Classe abstraite de base pour les equipements offensifs.
 *
 * <p>Les classes concretes (ex: Weapon, Spell) definissent le type exact
 * d'equipement attaque.</p>
 */
public abstract class OffensiveEquipment {
    // Type metier de l'equipement (ex: Weapon, Spell).
    private String type;
    // Bonus d'attaque apporte par l'equipement.
    private int attackLevel;
    // Nom lisible affiche au joueur.
    private String name;

    /**
     * Construit un equipement offensif par defaut.
     */
    // Initialise un objet neutre pour eviter un etat incomplet.
    public OffensiveEquipment() {
        // Valeurs de secours en l'absence de donnees metier.
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
    // Constructeur principal utilise par les classes filles concretes.
    public OffensiveEquipment(String type, String name, int attackLevel) {
        // Affecte les proprietes de base de l'equipement offensif.
        this.type = type;
        this.name = name;
        this.attackLevel = attackLevel;
    }

    /**
     * Retourne le type de l'equipement offensif.
     *
     * @return type de l'equipement
     */
    // Accesseur du type metier.
    public String getType() { return type; }

    /**
     * Met a jour le type de l'equipement offensif.
     *
     * @param type nouveau type d'equipement
     */
    // Mutateur du type metier.
    public void setType(String type) { this.type = type; }

    /**
     * Retourne la valeur d'attaque de l'equipement.
     *
     * @return niveau d'attaque de l'equipement
     */
    // Accesseur du bonus d'attaque.
    public int getAttackLevel() { return attackLevel; }

    /**
     * Met a jour la valeur d'attaque de l'equipement.
     *
     * @param attackLevel nouveau bonus d'attaque
     */
    // Mutateur du bonus d'attaque.
    public void setAttackLevel(int attackLevel) { this.attackLevel = attackLevel; }

    /**
     * Retourne le nom de l'equipement offensif.
     *
     * @return nom de l'equipement
     */
    // Accesseur du nom lisible.
    public String getName() { return name; }

    /**
     * Met a jour le nom de l'equipement offensif.
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
        return type + " '" + name + "' (attaque +" + attackLevel + ")";
    }
}
