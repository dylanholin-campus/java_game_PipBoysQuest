package serenadebird.pipboysquest.equipment.defensive;

/**
 * Potion standard de soin (type stimpak de base).
 */
public class StandardPotion extends Potion {
    public StandardPotion() {
        super("Potion standard", 2);
    }

    @Override
    public String toString() {
        return "Soin Fallout standard: " + super.toString();
    }
}

