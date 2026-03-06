package serenadebird.pipboysquest.enemy;

/**
 * Classe abstraite de base pour les ennemis du plateau.
 */
public abstract class Enemy {
    private String name;
    private int dangerLevel;

    public Enemy(String name, int dangerLevel) {
        this.name = name;
        this.dangerLevel = dangerLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    @Override
    public String toString() {
        return name + " (danger +" + dangerLevel + ")";
    }
}

