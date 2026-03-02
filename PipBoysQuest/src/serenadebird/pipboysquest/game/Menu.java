package serenadebird.pipboysquest.game;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.character.Warrior;
import serenadebird.pipboysquest.character.Wizard;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    public int showMainMenu() {
        System.out.println("=== PIP-BOY'S QUEST ===");
        System.out.println("1. Nouveau personnage");
        System.out.println("2. Quitter le jeu");
        return readIntInRange("Choix (1-2): ", 1, 2);
    }

    public Character createCharacter() {
        int typeChoice = chooseCharacterType();
        String name = askCharacterName();
        return buildCharacter(name, typeChoice);
    }

    public Character buildCharacter(String name, int typeChoice) {
        if (typeChoice == 1) {
            return new Warrior(name);
        }
        return new Wizard(name);
    }

    public int showCharacterMenu() {
        System.out.println("\n--- Menu Personnage ---");
        System.out.println("1. Afficher les infos");
        System.out.println("2. Modifier les infos");
        System.out.println("3. Demarrer la partie");
        System.out.println("4. Quitter le jeu");
        System.out.println("5. Retour au menu principal");
        return readIntInRange("Choix (1-5): ", 1, 5);
    }

    public void showCharacterInfo(Character character) {
        System.out.println(character.toString());
    }

    public int showModifyMenu() {
        System.out.println("\n--- Modifier ---");
        System.out.println("1. Changer le nom");
        System.out.println("2. Changer le type");
        System.out.println("3. Retour");
        return readIntInRange("Choix (1-3): ", 1, 3);
    }

    public String askCharacterName() {
        return readNonEmpty("Nom du personnage: ");
    }

    public int chooseCharacterType() {
        System.out.println("\n1. Warrior");
        System.out.println("2. Wizard");
        return readIntInRange("Choix (1-2): ", 1, 2);
    }

    public void close() {
        scanner.close();
    }

    public Scanner getScanner() { return scanner; }
    public void setScanner(Scanner scanner) { this.scanner = scanner; }

    @Override
    public String toString() {
        return "Menu{" +
                "scanner=" + scanner +
                '}';
    }

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

    public int showEndMenu() {
        System.out.println("\n--- Fin de partie ---");
        System.out.println("1. Recommencer une partie");
        System.out.println("2. Quitter le jeu");
        return readIntInRange("Choix (1-2): ", 1, 2);
    }
}
