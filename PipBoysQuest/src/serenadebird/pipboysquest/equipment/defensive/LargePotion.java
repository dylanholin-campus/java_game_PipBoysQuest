package serenadebird.pipboysquest.equipment.defensive;

/**
 * Grande potion de soin (type super-stimpak).
 */
public class LargePotion extends Potion {
    // Constructeur de preset: version renforcee de la potion standard.
    public LargePotion() {
        // Nom lore + bonus defensif superieur pour un soin plus puissant.
        super("Super Stimpack", 40);
    }

    @Override
    // Complete le rendu parent avec un label thematique Fallout.
    public String toString() {
        return "Soin Fallout majeur: " + super.toString();
    }
}

