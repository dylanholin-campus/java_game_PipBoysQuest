package serenadebird.pipboysquest.board.interactable;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.game.Menu;

/**
 * Interface définissant un objet avec lequel un personnage peut interagir sur le plateau.
 *
 * Critères : Définition d'une interface
 * Preuve de travail : L'interface est créée et ses méthodes ne sont pas implémentées
 */
public interface Interactable {
    /**
     * Définit l'action qui se produit lorsque le personnage interagit avec cet objet.
     * Cette méthode n'est pas implémentée ici (méthode abstraite).
     *
     * @param character Le personnage qui subit ou déclenche l'interaction.
     * @param menu La console de jeu pour afficher les messages au joueur.
     */
    void interact(Character character, Menu menu);
}

