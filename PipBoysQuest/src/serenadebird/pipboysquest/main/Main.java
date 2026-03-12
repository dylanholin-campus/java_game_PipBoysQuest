package serenadebird.pipboysquest.main;

import serenadebird.pipboysquest.game.Game;
import serenadebird.pipboysquest.game.Menu;
import serenadebird.pipboysquest.db.DatabaseManager;

public class Main {
    public Main() {}

    public static void main(String[] args) {
        GameWindow.startAndHookConsole();

        DatabaseManager db = new DatabaseManager();
        if (db.isDatabaseAvailable()) {
            db.seedCatalogsIfEmpty();
        }

        Menu menu = new Menu();
        Game game = new Game(menu, db);
        game.start();
        db.fermerConnexion();
    }
}
