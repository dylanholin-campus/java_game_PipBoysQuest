package serenadebird.pipboysquest.game;

import serenadebird.pipboysquest.board.Board;
import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.exception.OutOfBoardException;
import serenadebird.pipboysquest.db.DatabaseManager; // <- Nouvel import

public class Game {
    private Board board = new Board();
    private Dice dice = new Dice();
    private Menu menu;
    private Character player;
    private boolean isOver = false;

    // NOUVEAU : le gestionnaire de base de données
    private DatabaseManager db;

    /**
     * NOUVEAU CONSTRUCTEUR : prend le menu ET la base de données.
     */
    public Game(Menu gameMenu, DatabaseManager dbManager) {
        this.menu = gameMenu;
        this.db = dbManager; // On stocke la connexion pour l'utiliser plus tard
    }

    public void start() {
        boolean running = true;
        while (running) {
            int mainChoice = menu.showMainMenu();
            if (mainChoice == 1) {
                // 1. Création du personnage via le menu
                player = menu.createCharacter();

                // --- CONSIGNE : Sauvegarder le héros en BDD après création ---
                db.createHero(player);

                player.setBoardPosition(board.getStartPosition());
                running = handleCharacterMenu();
            } else {
                running = false;
            }
        }
        menu.close();
    }

    private boolean handleCharacterMenu() {
        boolean stayInCharacterMenu = true;
        while (stayInCharacterMenu) {
            int choice = menu.showCharacterMenu();
            if (choice == 1) {
                menu.showCharacterInfo(player);
            } else if (choice == 2) {
                handleModifyMenu();
            } else if (choice == 3) {
                boolean keepPlaying = playGame();
                if (!keepPlaying) {
                    return false;
                }
            } else if (choice == 4) {
                return false;
            } else {
                stayInCharacterMenu = false;
            }
        }
        return true;
    }

    private boolean playGame() {
        isOver = false;
        player.setBoardPosition(board.getStartPosition());
        while (!isOver) {
            playTurn();
        }
        int endChoice = menu.showEndMenu();
        return endChoice == 1;
    }

    private void handleModifyMenu() {
        boolean stay = true;
        while (stay) {
            int choice = menu.showModifyMenu();
            if (choice == 1) {
                String newName = menu.askCharacterName();
                player.setName(newName);

                // --- CONSIGNE : Mettre à jour en BDD après modification ---
                db.editHero(player);

            } else if (choice == 2) {
                // Si on change de type, on doit recréer l'objet mais garder le même ID BDD
                int oldId = player.getId();
                int typeChoice = menu.chooseCharacterType();
                player = menu.buildCharacter(player.getName(), typeChoice);
                player.setId(oldId); // On lui redonne son ID

                player.setBoardPosition(board.getStartPosition());

                // --- CONSIGNE : Mettre à jour en BDD après modification ---
                db.editHero(player);

            } else {
                stay = false;
            }
        }
    }

    public void playTurn() {
        System.out.println("\n--- Tour ---");
        System.out.println("Position: case " + player.getBoardPosition() + "/" + board.getSize());

        int steps = dice.roll();
        System.out.println("De: " + steps);

        try {
            movePlayer(steps);
        } catch (OutOfBoardException e) {
            menu.showOutOfBoardMessage(e.getMessage());
            player.setBoardPosition(board.getSize());
            System.out.println("Avancement: case " + player.getBoardPosition() + "/" + board.getSize());
            board.checkCell(player.getBoardPosition());
            System.out.println("Arrivee atteinte !");
            isOver = true;
            return;
        }

        System.out.println("Avancement: case " + player.getBoardPosition() + "/" + board.getSize());
        board.checkCell(player.getBoardPosition());

        // --- SIMULATION DE DEGATS POUR VALIDER LA CONSIGNE ---
        // (Juste pour l'exercice, on lui enlève 1 PV à chaque tour pour tester la méthode BDD)
        player.takeDamage(1);
        System.out.println("Le voyage vous fatigue... (-1 PV)");
        db.changeLifePoints(player); // <-- CONSIGNE : Mise à jour des PV en BDD

        if (player.getBoardPosition() >= board.getSize()) {
            System.out.println("Arrivee atteinte !");
            isOver = true;
        }
    }

    public void movePlayer(int steps) throws OutOfBoardException {
        int targetPosition = player.getBoardPosition() + steps;
        if (targetPosition > board.getSize()) {
            throw new OutOfBoardException(targetPosition, board.getSize());
        }
        player.move(steps);
    }

    // --- GETTERS ET SETTERS ---
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public Dice getDice() { return dice; }
    public void setDice(Dice dice) { this.dice = dice; }
    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }
    public Character getPlayer() { return player; }
    public void setPlayer(Character player) { this.player = player; }
    public boolean isOver() { return isOver; }
    public void setOver(boolean over) { isOver = over; }

    @Override
    public String toString() {
        return "Game{board=" + board + ", player=" + player + "}";
    }
}
