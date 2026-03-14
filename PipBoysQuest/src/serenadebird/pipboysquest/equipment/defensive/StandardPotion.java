package serenadebird.pipboysquest.equipment.defensive;

/**
 * Potion standard de soin (type stimpak de base).
 */
public class StandardPotion extends Potion {
    // Constructeur de preset: potion de soin commune, faible a moyen bonus.
    public StandardPotion() {
        // Nom lore + valeur defensive associee a ce tier d'objet.
        super("Stimpack", 20);
    }

    @Override
    // Complete le rendu parent avec un label thematique Fallout.
    public String toString() {
        return "Soin Fallout standard: " + super.toString();
    }
}

