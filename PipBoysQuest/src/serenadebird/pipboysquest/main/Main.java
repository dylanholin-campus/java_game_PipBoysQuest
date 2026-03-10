package serenadebird.pipboysquest.main;

import serenadebird.pipboysquest.game.Game;
import serenadebird.pipboysquest.game.Menu;
import serenadebird.pipboysquest.db.DatabaseManager;

public class Main {
    public Main() {}

    public static void main(String[] args) {
        GameWindow.startAndHookConsole();

        DatabaseManager db = new DatabaseManager();
        boolean databaseAvailable = db.isDatabaseAvailable();
        if (databaseAvailable) {
            System.out.println("Mode en ligne: MySQL detecte, sauvegarde active.");
            db.seedCatalogsIfEmpty();
            db.getHeroes();
        } else {
            System.out.println("Mode hors-ligne: MySQL indisponible, la sauvegarde est desactivee.");
        }

        Menu menu = new Menu();
        Game game = new Game(menu, db);
        game.start();
        db.fermerConnexion();
    }
}
