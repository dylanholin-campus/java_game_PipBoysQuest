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
    // Type metier (Warrior, Wizard...).
    private String type;
    // Nom visible du personnage.
    private String name;
    // Points de vie courants.
    private int healthLevel = 100;
    // Force d'attaque courante.
    private int attackStrength = 10;
    // Equipement offensif actuellement porte.
    private OffensiveEquipment offensiveEquipment;
    // Equipement defensif actuellement porte.
    private DefensiveEquipment defensiveEquipment;
    // Position 1-based du personnage sur le plateau.
    private int boardPosition = 1;
    // Identifiant BDD (0 tant que non persiste).
    private int id = 0;

    /**
     * Construit un personnage par defaut.
     */
    public Character() {
        // Valeurs de secours pour eviter un etat null.
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
        // Initialise les champs identitaires de base.
        this.type = type;
        this.name = name;
    }

    /**
     * Retourne l'action speciale definie par la sous-classe concrete.
     *
     * <p>Cette methode est abstraite car la classe {@code Character} ne peut
     * pas connaitre a l'avance l'action propre a chaque type de personnage.
     * Chaque classe enfant (par exemple {@code Warrior} ou {@code Wizard})
     * doit donc fournir sa propre implementation avec {@code @Override}.</p>
     *
     * <p>Dans le reste du code, on peut manipuler une reference de type
     * {@code Character} et appeler {@code getSpecialAction()} sans connaitre
     * la classe exacte de l'objet : c'est le polymorphisme.</p>
     *
     * @return libelle lisible de la competence speciale du personnage
     */
    public abstract String getSpecialAction();

    /**
     * Deplace le personnage sur le plateau.
     *
     * @param steps nombre de cases a avancer
     */
    public void move(int steps) {
        // Avance lineairement sur le plateau.
        boardPosition += steps;
        // Trace console pour suivi de partie.
        System.out.println(name + " avance a la case " + boardPosition);
    }

    /**
     * Retire des points de vie au personnage.
     *
     * @param amount degats recus
     */
    public void takeDamage(int amount) {
        // Retire les degats recus.
        healthLevel -= amount;
        if (healthLevel < 0) {
            // Clamp a 0 pour eviter des PV negatifs.
            healthLevel = 0;
        }
    }

    /**
     * Indique si le personnage est encore en vie.
     *
     * @return true si les points de vie sont superieurs a zero
     */
    public boolean isAlive() {
        // Le personnage est vivant tant que ses PV sont strictement positifs.
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
    // Retourne l'identifiant de persistence.
    public int getId() { return id; }
    // Met a jour l'identifiant de persistence.
    public void setId(int id) { this.id = id; }

    /**
     * Retourne le type d'affichage lisible pour l'interface joueur.
     */
    public String getDisplayType() {
        if ("Warrior".equalsIgnoreCase(type)) {
            return "Chevalier";
        }
        if ("Wizard".equalsIgnoreCase(type)) {
            return "Scribe";
        }
        return type;
    }

    /**
     * Retourne un resume lisible du personnage.
     *
     * @return etat complet du personnage
     */
    @Override
    public String toString() {
        // Construit un resume complet de l'etat courant du personnage.
        return "Personnage : " + name +
                ", Type : " + getDisplayType() +
                ", Niveau de vie : " + healthLevel +
                ", Force : " + attackStrength +
                ", Equipement offensif : " + offensiveEquipment +
                ", Equipement defensif : " + defensiveEquipment +
                ", Position : " + boardPosition;
    }

    /**
     * Retourne la limite de points de vie selon la classe.
     */
    public int getMaxHealthLevel() {
        // Caps de vie differencies selon la classe.
        if ("Warrior".equalsIgnoreCase(type)) {
            return 150;
        }
        if ("Wizard".equalsIgnoreCase(type)) {
            return 120;
        }
        return 120;
    }

    /**
     * Retourne la limite de force selon la classe.
     */
    public int getMaxAttackStrength() {
        // Caps de force differencies selon la classe.
        if ("Warrior".equalsIgnoreCase(type)) {
            return 60;
        }
        if ("Wizard".equalsIgnoreCase(type)) {
            return 50;
        }
        return 50;
    }

    /**
     * Retourne la force de base de la classe (sans equipement offensif).
     */
    public int getBaseAttackStrength() {
        if ("Warrior".equalsIgnoreCase(type)) {
            return 15;
        }
        if ("Wizard".equalsIgnoreCase(type)) {
            return 12;
        }
        return 10;
    }

    /**
     * Calcule la force effective base + equipement offensif sous cap de classe.
     */
    public int computeAttackWithEquipment(OffensiveEquipment equipment) {
        int bonus = equipment != null ? Math.max(0, equipment.getAttackLevel()) : 0;
        int raw = getBaseAttackStrength() + bonus;
        return Math.min(raw, getMaxAttackStrength());
    }

    /**
     * Augmente la vie sans depasser la limite de classe.
     */
    public void increaseHealthWithCap(int amount) {
        // Ignore les valeurs negatives puis limite au cap de classe.
        int next = healthLevel + Math.max(0, amount);
        healthLevel = Math.min(next, getMaxHealthLevel());
    }

    /**
     * Augmente la force sans depasser la limite de classe.
     */
    public void increaseAttackWithCap(int amount) {
        // Ignore les valeurs negatives puis limite au cap de classe.
        int next = attackStrength + Math.max(0, amount);
        attackStrength = Math.min(next, getMaxAttackStrength());
    }
}
