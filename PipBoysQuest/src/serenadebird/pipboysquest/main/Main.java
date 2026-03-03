package serenadebird.pipboysquest.main;

import serenadebird.pipboysquest.game.Game;
import serenadebird.pipboysquest.game.Menu;

/**
 * Point d'entree de l'application PipBoysQuest.
 * Cette classe instancie les composants principaux puis lance la boucle de jeu.
 */
public class Main {
    /**
     * Constructeur vide.
     */
    public Main() {
    }

    /**
     * Demarre le jeu console.
     *
     * @param args arguments de ligne de commande (non utilises)
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        Game game = new Game(menu);
        game.start();
    }

    /**
     * Retourne une representation textuelle minimale de la classe.
     *
     * @return description de l'instance
     */
    @Override
    public String toString() {
        return "Main{}";
    }
}
