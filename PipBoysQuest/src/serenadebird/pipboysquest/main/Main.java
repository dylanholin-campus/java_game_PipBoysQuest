package serenadebird.pipboysquest.main;

import serenadebird.pipboysquest.game.Game;
import serenadebird.pipboysquest.game.Menu;
import serenadebird.pipboysquest.db.DatabaseManager;

public class Main {
    public Main() {}

    public static void main(String[] args) {
        // 1. Initialisation de la BDD
        DatabaseManager db = new DatabaseManager();

        // 2. Affichage des héros existants (Consigne : au démarrage du jeu)
        db.getHeroes();
        System.out.println("--------------------------------\n");

        // 3. Lancement du jeu
        Menu menu = new Menu();

        // On passe 'db' au Game pour qu'il puisse sauvegarder pendant la partie
        Game game = new Game(menu, db);

        game.start();

        // 4. Fermeture propre quand on quitte le jeu
        db.fermerConnexion();
    }
}
