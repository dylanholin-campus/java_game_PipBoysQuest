package serenadebird.pipboysquest.exception;

public class OutOfBoardException extends Exception {
    public OutOfBoardException(int attemptedPosition, int lastCell) {
        super("Depassement du plateau: case " + attemptedPosition + " > case finale " + lastCell);
    }
}

