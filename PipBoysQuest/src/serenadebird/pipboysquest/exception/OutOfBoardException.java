package serenadebird.pipboysquest.exception;

/**
 * Exception levee quand un deplacement depasse la case finale du plateau.
 *
 * <p>Elle transporte un message explicite destine a l'interface de menu/log.</p>
 */
public class OutOfBoardException extends Exception {
    /**
     * Construit l'exception avec details de depassement.
     *
     * @param attemptedPosition position cible tentee
     * @param lastCell index de la derniere case du plateau
     */
    // Construit un message contextualise pour faciliter le debug et le retour joueur.
    public OutOfBoardException(int attemptedPosition, int lastCell) {
        // Message final affiche quand le deplacement depasse la taille du plateau.
        super("Depassement du plateau: case " + attemptedPosition + " > case finale " + lastCell);
    }
}
