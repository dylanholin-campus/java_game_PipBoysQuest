package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.character.Character;

/**
 * Classe abastraite de base pour toutes les cases du plateau
 */
public abstract class Cell {
    private int position;

    public Cell(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public abstract String toString();

    /**
     * Execute l'interaction entre la case et le personnage courant.
     */
    public abstract void interact(Character character);
}
