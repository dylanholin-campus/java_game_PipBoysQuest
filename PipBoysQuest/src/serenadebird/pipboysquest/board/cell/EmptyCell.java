package serenadebird.pipboysquest.board.cell;

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
}
