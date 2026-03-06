package serenadebird.pipboysquest.equipment.defensive;

/**
 * Grande potion de soin (type super-stimpak).
 */
public class LargePotion extends Potion {
    public LargePotion() {
        super("Grande potion", 5);
    }

    @Override
    public String toString() {
        return "Soin Fallout majeur: " + super.toString();
    }
}

