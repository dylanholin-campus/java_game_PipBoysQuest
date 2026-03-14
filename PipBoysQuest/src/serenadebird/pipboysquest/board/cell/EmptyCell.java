package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.game.Menu;

/**
 * Case neutre du plateau.
 *
 * <p>Aucune recompense ni menace: elle sert de case de transit.</p>
 */
public class EmptyCell extends Cell {
    // Recoit la position et la transmet a la classe de base Cell.
    public EmptyCell(int position) {
        super(position);
    }

    @Override
    // Retourne un texte court pour les logs d'apercu du plateau.
    public String toString() {
        return "Case " + getPosition() + " : case vide";
    }

    @Override
    // Interaction volontairement simple: pas d'effet sur les stats du joueur.
    public void interact(Character character, Menu menu) {
        System.out.println("Rien a signaler sur cette case.");
    }
}
