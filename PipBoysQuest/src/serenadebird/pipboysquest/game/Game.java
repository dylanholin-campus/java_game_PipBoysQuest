package serenadebird.pipboysquest.game;

import serenadebird.pipboysquest.board.Board;
import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.db.DatabaseManager;
import serenadebird.pipboysquest.exception.OutOfBoardException;

/**
 * Orchestrateur principal de la boucle de jeu.
 *
 * <p>Cette classe relie les menus, le plateau, le joueur courant et la
 * persistance BDD. Elle gere les transitions entre ecrans (menu principal,
 * menu personnage, tour de jeu) sans porter la logique d'affichage.</p>
 */
public class Game {
    // Plateau courant de la session.
    private Board board;
    // De utilise a chaque tour pour determiner le deplacement.
    private Dice dice = new Dice();
    // Facade I/O des menus console.
    private Menu menu;
    // Personnage actuellement controle.
    private Character player;
    // Etat de fin de partie en cours.
    private boolean isOver = false;
    // Acces persistence SQL (peut etre hors-ligne).
    private DatabaseManager db;
    // Compteur de tours pour l'affichage console type Pip-Boy.
    private int turnNumber = 1;

    /**
     * Construit une session de jeu avec ses dependances principales.
     *
     * @param gameMenu facade de saisie/affichage des menus
     * @param dbManager acces persistence (peut etre en mode hors-ligne)
     */
    public Game(Menu gameMenu, DatabaseManager dbManager) {
        // Injecte les dependances d'interface et de persistence.
        this.menu = gameMenu;
        this.db = dbManager;

        // Plateau complet 64 cases (chargement BDD si disponible, sinon fallback Java).
        this.board = new Board(64, db);
    }

    /**
     * Lance la boucle applicative de haut niveau.
     *
     * <p>Cycle: menu principal -> creation personnage -> menu personnage.
     * La methode se termine lorsque le joueur choisit de quitter.</p>
     */
    public void start() {
        // Boucle globale tant que le joueur ne quitte pas.
        boolean running = true;
        while (running) {
            // Menu principal: creation personnage ou sortie.
            int mainChoice = menu.showMainMenu();
            if (mainChoice == 1) {
                // Cree un nouveau hero via le menu.
                player = menu.createCharacter();
                // Persiste le hero (no-op si hors-ligne).
                db.createHero(player);

                // Repositionne le hero sur la case de depart avant toute action.
                player.setBoardPosition(board.getStartPosition());
                // Entre dans la boucle de gestion du personnage.
                running = handleCharacterMenu();
            } else {
                // Choix explicite de sortie.
                running = false;
            }
        }
        // Point de fermeture des ressources de menu.
        menu.close();
    }

    /**
     * Gere le menu de gestion du personnage (infos/modification/partie).
     *
     * @return true pour revenir au menu principal, false pour quitter
     */
    private boolean handleCharacterMenu() {
        // Boucle locale du menu personnage.
        boolean stayInCharacterMenu = true;
        while (stayInCharacterMenu) {
            int choice = menu.showCharacterMenu();
            if (choice == 1) {
                // Affiche les stats/equipements actuels.
                menu.showCharacterInfo(player);
            } else if (choice == 2) {
                // Ouvre le sous-menu de modification.
                handleModifyMenu();
            } else if (choice == 3) {
                // Lance une partie avec le hero courant.
                boolean keepPlaying = playGame();
                if (!keepPlaying) {
                    // Quitte totalement l'application si le joueur ne veut pas recommencer.
                    return false;
                }
            } else if (choice == 4) {
                // Quitte le jeu depuis le menu personnage.
                return false;
            } else {
                // Retour vers le menu principal.
                stayInCharacterMenu = false;
            }
        }
        // Continue l'application (menu principal).
        return true;
    }

    /**
     * Execute une partie complete jusqu'a interruption, victoire ou abandon.
     *
     * @return true si le joueur souhaite recommencer une partie, sinon false
     */
    private boolean playGame() {
        // Reinitialise l'etat de partie.
        isOver = false;
        turnNumber = 1;
        // Repart toujours de la case depart.
        player.setBoardPosition(board.getStartPosition());


        while (!isOver) {
            // Chaque tour demande une action explicite.
            int turnChoice = menu.showTurnMenu();
            if (turnChoice == 1) {
                // Execute le tour complet (de + interaction).
                playTurn();
            } else {
                System.out.println("Partie interrompue par le joueur.");
                // Force la sortie de la boucle de partie.
                isOver = true;
            }
        }
        // A la fin de la partie, propose recommencer/quitter.
        int endChoice = menu.showEndMenu();
        return endChoice == 1;
    }

    /**
     * Gere les options de modification du personnage courant.
     *
     * <p>Le changement de type reconstruit une instance concrete puis conserve
     * l'identifiant BDD du hero pour maintenir la coherence de persistance.</p>
     */
    private void handleModifyMenu() {
        // Boucle locale de modification tant que l'utilisateur reste dedans.
        boolean stay = true;
        while (stay) {
            int choice = menu.showModifyMenu();
            if (choice == 1) {
                // Demande puis applique un nouveau nom.
                String newName = menu.askCharacterName();
                player.setName(newName);
                // Persiste la modification en base.
                db.editHero(player);

            } else if (choice == 2) {
                // Sauvegarde l'ID pour conserver la meme ligne BDD apres reconstruction.
                int oldId = player.getId();
                int typeChoice = menu.chooseCharacterType();
                // Reconstruit une instance du type choisi en conservant le nom.
                player = menu.buildCharacter(player.getName(), typeChoice);
                player.setId(oldId);

                // Repositionne au depart pour eviter un etat incoherent apres changement de classe.
                player.setBoardPosition(board.getStartPosition());
                // Persiste l'etat remplace.
                db.editHero(player);

            } else {
                // Sortie du sous-menu.
                stay = false;
            }
        }
    }

    /**
     * Execute un tour de jeu complet.
     *
     * <p>Le tour suit l'ordre: affichage etat -> lancer de -> deplacement ->
     * interaction de case -> persistance des changements.</p>
     */
    private void playTurn() {
        // Affiche le debut de tour et la position actuelle.
        System.out.println("========== TOUR " + turnNumber + " ==========");
        System.out.println("Position: " + player.getBoardPosition() + "/" + board.getSize() + " | Vie: " + player.getHealthLevel() + " | Force: " + player.getAttackStrength());

        // Tire la valeur du de pour le deplacement.
        int steps = dice.roll();
        System.out.println("resultat du lancer : " + steps);

        try {
            // Tente d'appliquer le mouvement.
            movePlayer(steps);
        } catch (OutOfBoardException e) {
            // Depassement: informe le menu puis force la position a l'arrivee.
            menu.showOutOfBoardMessage(e.getMessage());
            player.setBoardPosition(board.getSize());
            System.out.println("Nouvelle position: case " + player.getBoardPosition() + "/" + board.getSize());
            board.checkCell(player.getBoardPosition());
            System.out.println("Arrivee atteinte !");
            // Ce tour termine la partie.
            isOver = true;
            return;
        }

        // Etat apres mouvement valide.
        System.out.println("Nouvelle position: case " + player.getBoardPosition() + "/" + board.getSize());
        // Affiche les infos de la case puis applique son interaction.
        board.checkCell(player.getBoardPosition());
        board.interactAt(player.getBoardPosition(), player, menu);

        if (!player.isAlive()) {
            System.out.println("Fin de partie: votre hero est tombe au combat.");
            isOver = true;
        }

        // Persiste les changements de stats/equipements apres interaction de case.
        db.editHero(player);

        if (player.getBoardPosition() >= board.getSize()) {
            System.out.println("Arrivee atteinte !");
            // Position finale atteinte: fin de partie.
            isOver = true;
        }

        if (!isOver) {
            // Passe au tour suivant uniquement si la partie continue.
            turnNumber++;
        }
    }

    /**
     * Deplace le joueur en validant les bornes du plateau.
     *
     * @param steps nombre de cases a avancer
     * @throws OutOfBoardException levee si la cible depasse la case finale
     */
    private void movePlayer(int steps) throws OutOfBoardException {
        // Calcule la cible sans modifier l'etat tant que la validation n'est pas faite.
        int targetPosition = player.getBoardPosition() + steps;
        if (targetPosition > board.getSize()) {
            // Signale explicitement le depassement.
            throw new OutOfBoardException(targetPosition, board.getSize());
        }
        // Mouvement valide.
        player.move(steps);
    }


    @Override
    // Resume court de l'etat de la session.
    public String toString() {
        return "Game{board=" + board + ", player=" + player + "}";
    }
}
