package serenadebird.pipboysquest.equipment.offensive;

/**
 * Sort d'eclair renforce, style techno-Fallout.
 */
public class Lightning extends Spell {
    public Lightning() {
        super("Eclair ionique", 5);
    }

    @Override
    public String toString() {
        return "Sort Fallout (Eclair): " + super.toString();
    }
}

