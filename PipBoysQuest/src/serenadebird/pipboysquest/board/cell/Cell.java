package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.board.interactable.Interactable;
import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.game.Menu;

/**
 * Classe abstraite de base pour toutes les cases du plateau.
 *
 * <p>Elle impose un contrat commun:
 * - une position sur le plateau
 * - un rendu texte
 * - une interaction via l'interface {@link Interactable}</p>
 */
public abstract class Cell implements Interactable {
    // Position indexee a partir de 1 sur le plateau lineaire.
    private int position;

    // Initialise la case avec sa position lors de sa creation.
    public Cell(int position) {
        this.position = position;
    }

    // Retourne la position courante de la case.
    public int getPosition() {
        return position;
    }

    // Met a jour la position (utile pour reconstruction/reordonnancement du plateau).
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    // Force chaque sous-classe a fournir un libelle lisible de la case.
    public abstract String toString();

    /**
     * Execute l'interaction entre la case et le personnage courant.
     * Cette méthode implémente la méthode de l'interface Interactable.
     * Dans cette classe abstraite, elle reste abstraite pour être implémentée dans les sous-classes.
     *
     * @param character personnage qui vient d'entrer sur la case
     * @param menu facade d'entree/sortie partagee pour toutes les saisies de la session
     */
    @Override
    public abstract void interact(Character character, Menu menu);
}
