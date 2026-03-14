package serenadebird.pipboysquest.enemy;

/**
 * Dragon adapte en version Fallout (creature mutante lourde).
 */
public class Dragon extends Enemy {
    // Construit un preset "dragon" avec nom et danger fixes.
    public Dragon() {
        super("Dragon irradie", 8);
    }

    @Override
    // Prefixe la description de base pour identifier clairement la famille d'ennemi.
    public String toString() {
        return "Dragon Fallout: " + super.toString();
    }
}

