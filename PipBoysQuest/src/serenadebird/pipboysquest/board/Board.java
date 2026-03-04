package serenadebird.pipboysquest.board;

import serenadebird.pipboysquest.board.cell.Cell;
import serenadebird.pipboysquest.board.cell.EmptyCell;
import serenadebird.pipboysquest.board.cell.EnemyCell;
import serenadebird.pipboysquest.board.cell.ItemsCell;

import java.util.ArrayList;

/**
 * Represente le plateau de jeu lineaire.
 *
 * <p>Le joueur progresse de la case de depart (1) a la case finale (size).</p>
 */
public class Board {
    private int size = 64;
    private final ArrayList<Cell> cells = new ArrayList<>();

    /**
     * Construit un plateau par defaut et initialise les cases speciales.
     */
    public Board() {
        initSpecialCells();
    }

    /**
     * Initialise le plateau 64 cases en mode Cell.
     */
    private void initSpecialCells() {
        cells.clear();
        for (int i = 1; i <= size; i++) {
            cells.add(new EmptyCell(i));
        }

        // Cases speciales pour exploiter le modele Cell sans casser la progression 1..64.
        if (size >= 12) {
            cells.set(11, new EnemyCell(12, "Raider"));
        }
        if (size >= 30) {
            cells.set(29, new ItemsCell(30, "WEAPON", "Laser Rifle", 5));
        }
        if (size >= 52) {
            cells.set(51, new ItemsCell(52, "POTION", "Stimpak", 4));
        }
    }

    /**
     * Retourne la case de depart.
     *
     * @return index de la case de depart
     */
    public int getStartPosition() {
        return 1;
    }

    /**
     * Affiche un message si la case est speciale.
     *
     * @param position position a verifier
     */
    public void checkCell(int position) {
        if (!isInside(position)) {
            return;
        }
        if (position == 1) {
            System.out.println("Case speciale: Depart");
            return;
        }
        if (position == size) {
            System.out.println("Case speciale: Arrivee");
            return;
        }

        Cell currentCell = cells.get(position - 1);
        if (!(currentCell instanceof EmptyCell)) {
            System.out.println("Case speciale: " + currentCell);
        }
    }

    /**
     * Verifie si une position appartient au plateau.
     *
     * @param position position a verifier
     * @return true si position dans [1, size]
     */
    public boolean isInside(int position) {
        return position >= 1 && position <= size;
    }

    /**
     * Retourne la taille actuelle du plateau.
     *
     * @return taille du plateau
     */
    public int getSize() { return size; }

    /**
     * Met a jour la taille et reinitialise les cases speciales.
     *
     * @param size nouvelle taille du plateau
     */
    public void setSize(int size) {
        this.size = size;
        initSpecialCells();
    }

    /**
     * Retourne la liste interne des cases.
     *
     * @return liste des cases
     */
    public ArrayList<Cell> getCells() { return cells; }

    /**
     * Retourne une representation textuelle du plateau.
     *
     * @return description du plateau
     */
    @Override
    public String toString() {
        return "Board{" +
                "size=" + size +
                '}';
    }
}
