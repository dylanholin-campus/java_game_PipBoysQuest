package serenadebird.pipboysquest.enemy;

/**
 * Gobelin adapte en version Fallout (petit pillard agressif).
 */
public class Goblin extends Enemy {
    // Construit un preset "gobelin" avec un danger plus faible.
    public Goblin() {
        super("Gobelin feral", 4);
    }

    @Override
    // Prefixe la description de base pour identifier clairement la famille d'ennemi.
    public String toString() {
        return "Gobelin Fallout: " + super.toString();
    }
}

