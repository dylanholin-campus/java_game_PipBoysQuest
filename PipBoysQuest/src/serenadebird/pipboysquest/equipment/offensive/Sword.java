package serenadebird.pipboysquest.equipment.offensive;

/**
 * Epee de l'univers Fallout (lame artisanale des wastelands).
 */
public class Sword extends Weapon {
    public Sword() {
        super("Epee de recuperation", 4);
    }

    @Override
    public String toString() {
        return "Epee Fallout: " + super.toString();
    }
}

