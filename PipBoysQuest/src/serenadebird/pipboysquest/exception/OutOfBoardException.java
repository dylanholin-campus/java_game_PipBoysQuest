package serenadebird.pipboysquest.exception;

/**
 * Exception levee quand un deplacement depasse la case finale du plateau.
 */
public class OutOfBoardException extends Exception {
    /**
     * Construit l'exception avec details de depassement.
     *
     * @param attemptedPosition position cible tentee
     * @param lastCell index de la derniere case du plateau
     */
    public OutOfBoardException(int attemptedPosition, int lastCell) {
        super("Depassement du plateau: case " + attemptedPosition + " > case finale " + lastCell);
    }
}
