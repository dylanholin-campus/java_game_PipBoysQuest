package serenadebird.pipboysquest.enemy;

/**
 * Classe abstraite de base pour les ennemis du plateau.
 *
 * <p>Chaque ennemi expose un nom lisible et un niveau de danger utilise
 * pour le feedback joueur et l'equilibrage des rencontres.</p>
 */
public abstract class Enemy {
    // Nom affiche dans les logs et descriptions de case.
    private String name;
    // Intensite relative de menace (plus la valeur est haute, plus l'ennemi est dangereux).
    private int dangerLevel;

    // Initialise les proprietes communes a toutes les variantes d'ennemis.
    public Enemy(String name, int dangerLevel) {
        this.name = name;
        this.dangerLevel = dangerLevel;
    }

    // Retourne le nom public de l'ennemi.
    public String getName() {
        return name;
    }

    // Met a jour le nom de l'ennemi.
    public void setName(String name) {
        this.name = name;
    }

    // Retourne le niveau de danger courant.
    public int getDangerLevel() {
        return dangerLevel;
    }

    // Met a jour le niveau de danger.
    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    @Override
    // Format standard reutilise par les sous-classes (Dragon/Goblin/Sorcerer).
    public String toString() {
        return name + " (danger +" + dangerLevel + ")";
    }
}

