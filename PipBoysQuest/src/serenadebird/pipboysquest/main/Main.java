package serenadebird.pipboysquest.main;

import serenadebird.pipboysquest.game.Game;
import serenadebird.pipboysquest.game.Menu;
import serenadebird.pipboysquest.db.DatabaseManager;

/**
 * Point d'entree de l'application: orchestre l'initialisation UI, BDD et moteur de jeu.
 */
public class Main {
    public Main() {}

    /**
     * Initialise l'environnement du jeu puis lance la boucle principale.
     *
     * @param args arguments CLI non utilises pour le moment
     */
    public static void main(String[] args) {
        // Redirige les E/S console vers la fenetre Swing pour conserver une UX unifiee.
        GameWindow.startAndHookConsole();

        DatabaseManager db = new DatabaseManager();
        if (db.isDatabaseAvailable()) {
            // Evite les doublons: le seed ne s'applique que sur des tables vides.
            db.seedCatalogsIfEmpty();
        }

        Menu menu = new Menu();
        Game game = new Game(menu, db);
        game.start();

        // Ferme explicitement la connexion en fin de session (no-op en mode hors-ligne).
        db.fermerConnexion();
    }
}
