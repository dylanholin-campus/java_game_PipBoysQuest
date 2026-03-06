package serenadebird.pipboysquest.enemy;

/**
 * Gobelin adapte en version Fallout (petit pillard agressif).
 */
public class Goblin extends Enemy {
    public Goblin() {
        super("Gobelin feral", 4);
    }

    @Override
    public String toString() {
        return "Gobelin Fallout: " + super.toString();
    }
}

