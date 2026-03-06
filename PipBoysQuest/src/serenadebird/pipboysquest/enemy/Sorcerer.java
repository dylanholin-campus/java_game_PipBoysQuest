package serenadebird.pipboysquest.enemy;

/**
 * Sorcier adapte en version Fallout (technomancien des Terres desolees).
 */
public class Sorcerer extends Enemy {
    public Sorcerer() {
        super("Sorcier techno-raider", 6);
    }

    @Override
    public String toString() {
        return "Sorcier Fallout: " + super.toString();
    }
}

