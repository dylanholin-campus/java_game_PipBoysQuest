package serenadebird.pipboysquest.main;

import serenadebird.pipboysquest.game.Game;
import serenadebird.pipboysquest.game.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        Game game = new Game(menu);
        game.start();
    }

    @Override
    public String toString() {
        return "Main{}";
    }
}
