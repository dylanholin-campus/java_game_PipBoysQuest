package serenadebird.pipboysquest.game;

import serenadebird.pipboysquest.board.Board;
import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.exception.OutOfBoardException;

public class Game {
    private Board board = new Board();
    private Dice dice = new Dice();
    private Menu menu;
    private Character player;
    private boolean isOver = false;

    public Game(Menu menu) {
        this.menu = menu;
    }

    public void start() {
        boolean running = true;
        while (running) {
            int mainChoice = menu.showMainMenu();
            if (mainChoice == 1) {
                player = menu.createCharacter();
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
            } else if (choice == 2) {
                int typeChoice = menu.chooseCharacterType();
                player = menu.buildCharacter(player.getName(), typeChoice);
                player.setBoardPosition(board.getStartPosition());
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
        return "Game{" +
                "board=" + board +
                ", dice=" + dice +
                ", menu=" + menu +
                ", player=" + player +
                ", isOver=" + isOver +
                '}';
    }
}
