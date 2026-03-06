package serenadebird.pipboysquest.board;

import serenadebird.pipboysquest.board.cell.Cell;
import serenadebird.pipboysquest.board.cell.EmptyCell;
import serenadebird.pipboysquest.board.cell.EnemyCell;
import serenadebird.pipboysquest.board.cell.ItemsCell;
import serenadebird.pipboysquest.enemy.Dragon;
import serenadebird.pipboysquest.enemy.Goblin;
import serenadebird.pipboysquest.enemy.Sorcerer;
import serenadebird.pipboysquest.equipment.defensive.LargePotion;
import serenadebird.pipboysquest.equipment.defensive.StandardPotion;
import serenadebird.pipboysquest.equipment.offensive.Fireball;
import serenadebird.pipboysquest.equipment.offensive.Lightning;
import serenadebird.pipboysquest.equipment.offensive.Mace;
import serenadebird.pipboysquest.equipment.offensive.Sword;

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
     * Construit un plateau 64 cases par defaut.
     */
    public Board() {
        initSpecialCells();
    }

    /**
     * Construit un plateau personnalise (ex: 10 cases pour la version simplifiee).
     *
     * @param size taille souhaitee
     */
    public Board(int size) {
        this.size = size;
        initSpecialCells();
    }

    /**
     * Initialise les cases speciales selon la taille du plateau.
     */
    private void initSpecialCells() {
        cells.clear();
        for (int i = 1; i <= size; i++) {
            cells.add(new EmptyCell(i));
        }

        if (size == 10) {
            initTrainingBoard();
            return;
        }

        // Plateau principal 64 cases.
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
     * Initialise le plateau simplifie (10 cases) avec une instance de chaque type demande.
     */
    private void initTrainingBoard() {
        cells.set(1, new EnemyCell(2, new Dragon()));
        cells.set(2, new EnemyCell(3, new Sorcerer()));
        cells.set(3, new EnemyCell(4, new Goblin()));

        cells.set(4, new ItemsCell(5, new Mace()));
        cells.set(5, new ItemsCell(6, new Sword()));
        cells.set(6, new ItemsCell(7, new Lightning()));
        cells.set(7, new ItemsCell(8, new Fireball()));
        cells.set(8, new ItemsCell(9, new StandardPotion()));
        cells.set(9, new ItemsCell(10, new LargePotion()));
    }

    /**
     * Affiche toutes les cases du plateau via leur toString().
     */
    public void printCellsOverview() {
        System.out.println("\n--- Apercu du plateau (" + size + " cases) ---");
        for (Cell cell : cells) {
            System.out.println(cell);
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
