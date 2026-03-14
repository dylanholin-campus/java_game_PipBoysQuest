package serenadebird.pipboysquest.enemy;

/**
 * Sorcier adapte en version Fallout (technomancien des Terres desolees).
 */
public class Sorcerer extends Enemy {
    // Construit un preset "sorcier" avec un danger intermediaire.
    public Sorcerer() {
        super("Sorcier techno-raider", 6);
    }

    @Override
    // Prefixe la description de base pour identifier clairement la famille d'ennemi.
    public String toString() {
        return "Sorcier Fallout: " + super.toString();
    }
}

