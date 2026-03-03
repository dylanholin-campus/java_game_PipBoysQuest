package serenadebird.pipboysquest.game;

import serenadebird.pipboysquest.board.Board;
import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.exception.OutOfBoardException;

/**
 * Contient la logique de jeu: menus, boucle de partie et deplacements.
 */
public class Game {
    private Board board = new Board();
    private Dice dice = new Dice();
    private Menu menu;
    private Character player;
    private boolean isOver = false;

    /**
     * Construit une partie avec l'instance de menu utilisee pour les interactions.
     *
     * @param menu menu d'entree/sortie console
     */
    public Game(Menu menu) {
        this.menu = menu;
    }

    /**
     * Demarre la boucle principale de l'application.
     */
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

    /**
     * Gere les actions disponibles depuis le menu personnage.
     *
     * @return true pour revenir au menu principal, false pour quitter
     */
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

    /**
     * Execute une partie complete jusqu'a la fin du plateau.
     *
     * @return true si le joueur veut recommencer, false sinon
     */
    private boolean playGame() {
        isOver = false;
        player.setBoardPosition(board.getStartPosition());
        while (!isOver) {
            playTurn();
        }
        int endChoice = menu.showEndMenu();
        return endChoice == 1;
    }

    /**
     * Gere la modification du personnage courant.
     */
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

    /**
     * Joue un tour: lancer de de, deplacement, verification d'arrivee.
     */
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

    /**
     * Deplace le joueur et leve une exception si la case cible depasse le plateau.
     *
     * @param steps nombre de cases a avancer
     * @throws OutOfBoardException si la case cible depasse la case finale
     */
    public void movePlayer(int steps) throws OutOfBoardException {
        int targetPosition = player.getBoardPosition() + steps;
        if (targetPosition > board.getSize()) {
            throw new OutOfBoardException(targetPosition, board.getSize());
        }
        player.move(steps);
    }

    /**
     * Retourne le plateau utilise par la partie.
     *
     * @return plateau de jeu
     */
    public Board getBoard() { return board; }

    /**
     * Remplace le plateau utilise par la partie.
     *
     * @param board nouveau plateau de jeu
     */
    public void setBoard(Board board) { this.board = board; }

    /**
     * Retourne le de utilise pour les tours.
     *
     * @return de de jeu
     */
    public Dice getDice() { return dice; }

    /**
     * Remplace le de utilise pour les tours.
     *
     * @param dice nouveau de
     */
    public void setDice(Dice dice) { this.dice = dice; }

    /**
     * Retourne le menu associe a la partie.
     *
     * @return menu associe a la partie
     */
    public Menu getMenu() { return menu; }

    /**
     * Remplace le menu associe a la partie.
     *
     * @param menu nouveau menu
     */
    public void setMenu(Menu menu) { this.menu = menu; }

    /**
     * Retourne le personnage joueur courant.
     *
     * @return personnage joueur
     */
    public Character getPlayer() { return player; }

    /**
     * Remplace le personnage joueur courant.
     *
     * @param player nouveau personnage joueur
     */
    public void setPlayer(Character player) { this.player = player; }

    /**
     * Indique si la partie est terminee.
     *
     * @return true si la partie est terminee
     */
    public boolean isOver() { return isOver; }

    /**
     * Met a jour l'etat de fin de partie.
     *
     * @param over nouvel etat de fin de partie
     */
    public void setOver(boolean over) { isOver = over; }

    /**
     * Retourne une representation textuelle de l'etat de jeu.
     *
     * @return description de l'instance
     */
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
