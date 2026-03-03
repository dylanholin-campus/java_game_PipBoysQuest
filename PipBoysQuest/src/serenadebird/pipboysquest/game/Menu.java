package serenadebird.pipboysquest.game;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.character.Warrior;
import serenadebird.pipboysquest.character.Wizard;

import java.util.Scanner;

/**
 * Gere l'affichage console des menus et la lecture des saisies utilisateur.
 */
public class Menu {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Construit un menu base sur l'entree standard.
     */
    public Menu() {
    }

    /**
     * Affiche le menu principal.
     *
     * @return choix utilisateur entre 1 et 2
     */
    public int showMainMenu() {
        System.out.println("=== PIP-BOY'S QUEST ===");
        System.out.println("1. Nouveau personnage");
        System.out.println("2. Quitter le jeu");
        return readIntInRange("Choix (1-2): ", 1, 2);
    }

    /**
     * Lance la creation d'un personnage en demandant type et nom.
     *
     * @return personnage cree
     */
    public Character createCharacter() {
        int typeChoice = chooseCharacterType();
        String name = askCharacterName();
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
        if (typeChoice == 1) {
            return new Warrior(name);
        }
        return new Wizard(name);
    }

    /**
     * Affiche le menu de gestion du personnage.
     *
     * @return choix utilisateur entre 1 et 5
     */
    public int showCharacterMenu() {
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
        System.out.println(character.toString());
    }

    /**
     * Affiche un message de depassement de plateau.
     *
     * @param message message de l'exception
     */
    public void showOutOfBoardMessage(String message) {
        System.out.println("Depassement detecte: " + message);
    }

    /**
     * Affiche le sous-menu de modification du personnage.
     *
     * @return choix utilisateur entre 1 et 3
     */
    public int showModifyMenu() {
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
        return readNonEmpty("Nom du personnage: ");
    }

    /**
     * Demande le type de personnage.
     *
     * @return 1 pour Warrior, 2 pour Wizard
     */
    public int chooseCharacterType() {
        System.out.println("\n1. Warrior");
        System.out.println("2. Wizard");
        return readIntInRange("Choix (1-2): ", 1, 2);
    }

    /**
     * Ferme le scanner de saisie console.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Retourne le scanner utilise pour lire les saisies utilisateur.
     *
     * @return scanner courant
     */
    public Scanner getScanner() { return scanner; }

    /**
     * Remplace le scanner utilise pour la lecture des saisies.
     *
     * @param scanner nouveau scanner
     */
    public void setScanner(Scanner scanner) { this.scanner = scanner; }

    /**
     * Retourne une representation textuelle du menu.
     *
     * @return description de l'instance
     */
    @Override
    public String toString() {
        return "Menu{" +
                "scanner=" + scanner +
                '}';
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
            System.out.print(prompt);
            String raw = scanner.nextLine();
            try {
                int value = Integer.parseInt(raw.trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // loop again
            }
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
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Saisie vide, recommencez.");
        }
    }

    /**
     * Affiche le menu de fin de partie.
     *
     * @return 1 pour recommencer, 2 pour quitter
     */
    public int showEndMenu() {
        System.out.println("\n--- Fin de partie ---");
        System.out.println("1. Recommencer une partie");
        System.out.println("2. Quitter le jeu");
        return readIntInRange("Choix (1-2): ", 1, 2);
    }
}
