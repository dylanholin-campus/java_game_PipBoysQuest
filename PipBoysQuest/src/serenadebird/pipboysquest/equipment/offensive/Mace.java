package serenadebird.pipboysquest.equipment.offensive;

/**
 * Massue de l'univers Fallout (arme lourde de melee).
 */
public class Mace extends Weapon {
    public Mace() {
        super("Massue rouillee", 3);
    }

    @Override
    public String toString() {
        return "Massue Fallout: " + super.toString();
    }
}

