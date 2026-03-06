package serenadebird.pipboysquest.enemy;

/**
 * Dragon adapte en version Fallout (creature mutante lourde).
 */
public class Dragon extends Enemy {
    public Dragon() {
        super("Dragon irradie", 8);
    }

    @Override
    public String toString() {
        return "Dragon Fallout: " + super.toString();
    }
}

