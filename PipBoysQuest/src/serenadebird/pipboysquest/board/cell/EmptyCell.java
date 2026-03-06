package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.character.Character;

/**
 * Case vide.
 */
public class EmptyCell extends Cell {
    public EmptyCell(int position) {
        super(position);
    }
    @Override
    public String toString() {
        return "Case " + getPosition() + " : case vide";
    }

    @Override
    public void interact(Character character) {
        System.out.println("Rien a signaler sur cette case.");
    }
}
