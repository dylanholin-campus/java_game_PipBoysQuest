package serenadebird.pipboysquest.game;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.character.Warrior;
import serenadebird.pipboysquest.character.Wizard;

import java.util.Scanner;

/**
 * Gere l'affichage console des menus et la lecture des saisies utilisateur.
 */
public class Menu {
    // Scanner unique pour toute la session afin d'eviter des lectures concurrentes sur System.in.
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Construit un menu base sur l'entree standard.
     */
    public Menu() {
        // Aucune initialisation persistante: lecture basee sur System.in a la demande.
    }

    /**
     * Affiche le menu principal.
     *
     * @return choix utilisateur entre 1 et 2
     */
    public int showMainMenu() {
        // Entree principale du jeu.
        System.out.println("=== PIP-BOY'S QUEST ===");
        System.out.println("1. Nouveau personnage");
        System.out.println("2. Quitter le jeu");
        // Valide strictement la plage autorisee.
        return readIntInRange("Choix (1-2): ", 1, 2);
    }

    /**
     * Lance la creation d'un personnage en demandant type et nom.
     *
     * @return personnage cree
     */
    public Character createCharacter() {
        // Demande d'abord la classe puis le nom.
        int typeChoice = chooseCharacterType();
        String name = askCharacterName();
        // Construit l'instance concrete correspondante.
        return buildCharacter(name, typeChoice);
    }

    /**
     * Construit un personnage selon le type choisi.
     *
     * @param name nom du personnage
     * @param typeChoice 1 pour Warrior, 2 pour Wizard
     * @return instance concrete du personnage
     */
    public Character buildCharacter(String name, int typeChoice) {
        // 1 => Warrior.
        if (typeChoice == 1) {
            return new Warrior(name);
        }
        // 2 => Wizard (fallback puisque l'entree est deja validee).
        return new Wizard(name);
    }

    /**
     * Affiche le menu de gestion du personnage.
     *
     * @return choix utilisateur entre 1 et 5
     */
    public int showCharacterMenu() {
        // Menu de gestion avant de lancer une partie.
        System.out.println("\n--- Menu Personnage ---");
        System.out.println("1. Afficher les infos");
        System.out.println("2. Modifier les infos");
        System.out.println("3. Demarrer la partie");
        System.out.println("4. Quitter le jeu");
        System.out.println("5. Retour au menu principal");
        return readIntInRange("Choix (1-5): ", 1, 5);
    }

    /**
     * Affiche les informations du personnage courant.
     *
     * @param character personnage a afficher
     */
    public void showCharacterInfo(Character character) {
        // Delegue au toString du personnage pour garder un affichage centralise.
        System.out.println(character.toString());
    }

    /**
     * Affiche un message de depassement de plateau.
     *
     * @param message message de l'exception
     */
    public void showOutOfBoardMessage(String message) {
        // Message contextualise pour les depassements de plateau.
        System.out.println("Depassement detecte: " + message);
    }

    /**
     * Affiche le sous-menu de modification du personnage.
     *
     * @return choix utilisateur entre 1 et 3
     */
    public int showModifyMenu() {
        // Sous-menu dedie aux changements de profil.
        System.out.println("\n--- Modifier ---");
        System.out.println("1. Changer le nom");
        System.out.println("2. Changer le type");
        System.out.println("3. Retour");
        return readIntInRange("Choix (1-3): ", 1, 3);
    }

    /**
     * Lit un nom de personnage non vide.
     *
     * @return nom valide
     */
    public String askCharacterName() {
        // Reutilise le validateur de saisie non vide.
        return readNonEmpty("Nom du personnage: ");
    }

    /**
     * Demande le type de personnage.
     *
     * @return 1 pour Warrior, 2 pour Wizard
     */
    public int chooseCharacterType() {
        // Choix binaire entre les deux classes disponibles.
        System.out.println("\n1. Chevalier");
        System.out.println("2. Scribe");
        return readIntInRange("Choix (1-2): ", 1, 2);
    }

    /**
     * Ferme les ressources du menu.
     *
     * <p>Aucune ressource persistante n'est conservee dans cette classe.</p>
     */
    public void close() {
        // Pas d'etat interne a fermer (pas d'attribut Scanner).
    }

    /**
     * Retourne une representation textuelle du menu.
     *
     * @return description de l'instance
     */
    @Override
    public String toString() {
        // Classe stateless, representation minimale.
        return "Menu{}";
    }

    /**
     * Lit un entier et verifie qu'il est dans un intervalle.
     *
     * @param prompt texte affiche avant la saisie
     * @param min borne minimale incluse
     * @param max borne maximale incluse
     * @return entier valide compris entre min et max
     */
    private int readIntInRange(String prompt, int min, int max) {
        while (true) {
            // Affiche le prompt puis lit la ligne brute.
            System.out.print(prompt);
            System.out.flush();
            String raw = scanner.nextLine();
            try {
                // Conversion robuste avec trim des espaces.
                int value = Integer.parseInt(raw.trim());
                if (value >= min && value <= max) {
                    // Retourne uniquement une valeur dans l'intervalle attendu.
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // loop again
            }
            // Feedback utilisateur en cas d'echec de validation.
            System.out.println("Choix invalide, recommencez.");
        }
    }

    /**
     * Lit une chaine non vide.
     *
     * @param prompt texte affiche avant la saisie
     * @return texte valide non vide
     */
    private String readNonEmpty(String prompt) {
        while (true) {
            // Affiche le prompt puis lit une saisie nettoyee.
            System.out.print(prompt);
            System.out.flush();
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                // Retourne la premiere saisie non vide.
                return value;
            }
            // Informe l'utilisateur et relance la boucle.
            System.out.println("Saisie vide, recommencez.");
        }
    }

    /**
     * Affiche le menu de fin de partie.
     *
     * @return 1 pour recommencer, 2 pour quitter
     */
    public int showEndMenu() {
        // Menu affiche a la fin d'une partie.
        System.out.println("\n--- Fin de partie ---");
        System.out.println("1. Retour au Menu Personnage");
        System.out.println("2. Quitter le jeu");
        return readIntInRange("Choix (1-2): ", 1, 2);
    }

    /**
     * Affiche les actions disponibles avant de jouer un tour.
     *
     * @return 1 pour lancer le de, 2 pour interrompre la partie en cours
     */
    public int showTurnMenu() {
        // Menu d'action a chaque tour de jeu.
        System.out.println("Appuyez sur 1 pour faire un lancer");
        System.out.println("2. Terminer la partie");
        return readIntInRange("Action (1-2): ", 1, 2);
    }

    /**
     * Affiche les options disponibles pendant un combat.
     *
     * @param fleeChance pourcentage de chance de fuite affiche au joueur
     * @return 1 pour attaquer, 2 pour tenter de fuir
     */
    public int showCombatMenu(int fleeChance) {
        // Reproduit le prompt de combat court attendu dans l'UI Pip-Boy.
        System.out.println("1) Attaquer");
        System.out.println("2) Tenter de fuir (" + fleeChance + "% de chance)");
        return readIntInRange("Action (1-2): ", 1, 2);
    }

    /**
     * Lit une confirmation oui/non de facon robuste.
     *
     * @param prompt question affichee a l'utilisateur
     * @return true si l'utilisateur confirme, sinon false
     */
    public boolean askYesNo(String prompt) {
        while (true) {
            // Affiche la question puis lit la reponse brute.
            System.out.print(prompt);
            System.out.flush();
            String answer = scanner.nextLine().trim();
            // Accepte plusieurs variantes de confirmation.
            if ("o".equalsIgnoreCase(answer) || "oui".equalsIgnoreCase(answer)
                    || "y".equalsIgnoreCase(answer) || "yes".equalsIgnoreCase(answer)) {
                return true;
            }
            // Accepte plusieurs variantes de refus.
            if ("n".equalsIgnoreCase(answer) || "non".equalsIgnoreCase(answer)
                    || "no".equalsIgnoreCase(answer)) {
                return false;
            }
            // Boucle jusqu'a obtenir une reponse conforme.
            System.out.println("Reponse invalide. Tapez 'o' ou 'n'.");
        }
    }
}
