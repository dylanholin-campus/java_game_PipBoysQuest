package serenadebird.pipboysquest.board;

/**
 * Represente le plateau de jeu lineaire.
 *
 * <p>Le joueur progresse de la case de depart (1) a la case finale (size).</p>
 */
public class Board {
    private int size = 64;
    private String[] cells = new String[size + 1];

    /**
     * Construit un plateau par defaut et initialise les cases speciales.
     */
    public Board() {
        initSpecialCells();
    }

    /**
     * Initialise les cases de depart et d'arrivee.
     */
    private void initSpecialCells() {
        cells[1] = "Depart";
        cells[size] = "Arrivee";
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
        if (position >= 1 && position <= size && cells[position] != null) {
            System.out.println("Case speciale: " + cells[position]);
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
        this.cells = new String[size + 1];
        initSpecialCells();
    }

    /**
     * Retourne le tableau interne des cases.
     *
     * @return tableau des cases
     */
    public String[] getCells() { return cells; }

    /**
     * Remplace le tableau interne des cases.
     *
     * @param cells nouveau tableau des cases
     */
    public void setCells(String[] cells) { this.cells = cells; }

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
