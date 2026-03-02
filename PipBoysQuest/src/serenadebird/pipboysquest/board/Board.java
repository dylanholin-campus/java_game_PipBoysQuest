package serenadebird.pipboysquest.board;

public class Board {
    private int size = 64;
    private String[] cells = new String[size + 1];

    public Board() {
        initSpecialCells();
    }

    private void initSpecialCells() {
        cells[1] = "Depart";
        cells[size] = "Arrivee";
    }

    public int getStartPosition() {
        return 1;
    }

    public void checkCell(int position) {
        if (position >= 1 && position <= size && cells[position] != null) {
            System.out.println("Case speciale: " + cells[position]);
        }
    }

    public boolean isInside(int position) {
        return position >= 1 && position <= size;
    }

    public int getSize() { return size; }
    public void setSize(int size) {
        this.size = size;
        this.cells = new String[size + 1];
        initSpecialCells();
    }

    public String[] getCells() { return cells; }
    public void setCells(String[] cells) { this.cells = cells; }

    @Override
    public String toString() {
        return "Board{" +
                "size=" + size +
                '}';
    }
}
