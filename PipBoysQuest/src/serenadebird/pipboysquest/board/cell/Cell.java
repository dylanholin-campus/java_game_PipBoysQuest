package serenadebird.pipboysquest.board.cell;

/**
 * Classe abastraite de base pour toutes les cases du plateau
 */
public abstract class Cell {
    private int position;

    public Cell(int position) {
        this.position = position;
    }

public int  getPosition() {
    return position;
}

public void setPosition(int position) {
    this.position = position;
}

@Override
    public abstract String toString();
}
