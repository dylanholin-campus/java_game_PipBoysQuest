package serenadebird.pipboysquest.equipment.offensive;

/**
 * Sort de boule de feu version post-apo.
 */
public class Fireball extends Spell {
    public Fireball() {
        super("Boule de feu plasma", 6);
    }

    @Override
    public String toString() {
        return "Sort Fallout (Boule de feu): " + super.toString();
    }
}

