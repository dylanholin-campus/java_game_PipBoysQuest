package serenadebird.pipboysquest.board;

import serenadebird.pipboysquest.board.cell.Cell;
import serenadebird.pipboysquest.board.cell.EmptyCell;
import serenadebird.pipboysquest.board.cell.EnemyCell;
import serenadebird.pipboysquest.board.cell.ItemsCell;
import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.db.DatabaseManager;
import serenadebird.pipboysquest.enemy.Dragon;
import serenadebird.pipboysquest.enemy.Goblin;
import serenadebird.pipboysquest.enemy.Sorcerer;
import serenadebird.pipboysquest.equipment.defensive.PotionCatalog;
import serenadebird.pipboysquest.equipment.offensive.Weapon;
import serenadebird.pipboysquest.equipment.offensive.WeaponCatalog;
import serenadebird.pipboysquest.game.Menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represente le plateau de jeu lineaire.
 */
public class Board {
    // Taille du plateau (64 par defaut pour la partie complete).
    private int size = 64;
    // Tableau lineaire de cases; index 0 correspond a la position 1.
    private final ArrayList<Cell> cells = new ArrayList<>();
    // Acces BDD optionnel pour persister le layout genere.
    private DatabaseManager db;
    // Source d'aleatoire unique pour melanger cases et positions.
    private final Random random = new Random();

    // Constructeur par defaut: plateau standard avec initialisation immediate.
    public Board() {
        initSpecialCells();
    }

    // Constructeur avec taille personnalisee (sans liaison BDD).
    public Board(int size) {
        this.size = size;
        initSpecialCells();
    }

    // Constructeur complet: taille + eventuelle persistance du layout.
    public Board(int size, DatabaseManager db) {
        this.size = size;
        this.db = db;
        initSpecialCells();
    }

    /**
     * Initialise le plateau selon le mode de jeu cible.
     *
     * <p>- 10 cases: plateau d'entrainement deterministe
     * - 64+ cases: plateau complet aleatoire
     * - sinon: fallback minimal avec une rencontre</p>
     */
    private void initSpecialCells() {
        // Repart toujours d'un plateau vide pour garantir un etat coherent.
        cells.clear();
        // Cree une base 100% vide avant d'injecter les cases speciales.
        for (int i = 1; i <= size; i++) {
            cells.add(new EmptyCell(i));
        }

        // Mode entrainement: layout fixe et previsible.
        if (size == 10) {
            initTrainingBoard();
            return;
        }

        // Mode principal: generation aleatoire complete.
        if (size >= 64) {
            initRandomFullBoard();
            return;
        }

        // Fallback minimal pour petites tailles: au moins une interaction.
        if (size >= 3) {
            cells.set(2, new EnemyCell(3, new Goblin()));
        }
    }

    /**
     * Genere un plateau 64 cases aleatoire en conservant les quantites fixes.
     */
    private void initRandomFullBoard() {
        // Liste des codes de cases speciales avec quantites fixes.
        List<String> codes = new ArrayList<>();
        addCodes(codes, "ENEMY_DRAGON", 4);
        addCodes(codes, "ENEMY_SORCERER", 10);
        addCodes(codes, "ENEMY_GOBLIN", 10);

        for (WeaponCatalog.Entry weaponEntry : WeaponCatalog.entries()) {
            addCodes(codes, weaponEntry.getBoardCode(), weaponEntry.getQuantity());
        }
        for (PotionCatalog.Entry potionEntry : PotionCatalog.entries()) {
            addCodes(codes, potionEntry.getBoardCode(), potionEntry.getQuantity());
        }

        // Liste de toutes les positions 1..size a melanger.
        List<Integer> positions = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            positions.add(i);
        }

        // Melange independant pour distribuer aleatoirement les types et les positions.
        Collections.shuffle(codes, random);
        Collections.shuffle(positions, random);

        // Conserve le layout pour eventuelle sauvegarde BDD.
        Map<Integer, String> generatedLayout = new HashMap<>();
        // Associe chaque code a une position melangee.
        for (int i = 0; i < codes.size(); i++) {
            int position = positions.get(i);
            String code = codes.get(i);
            // Construit la case concrete a partir du code.
            Cell cell = buildCellFromCode(position, code);
            if (cell != null) {
                // Position -> index: conversion 1-based vers 0-based.
                cells.set(position - 1, cell);
                generatedLayout.put(position, code);
            }
        }

        // Si la BDD est disponible, persiste le layout genere pour reutilisation.
        if (db != null) {
            db.replaceBoardLayout(generatedLayout);
        }
    }

    /**
     * Ajoute plusieurs occurrences d'un code de case dans la liste de generation.
     */
    private void addCodes(List<String> target, String code, int count) {
        // Ajoute count fois le meme code dans la collection cible.
        for (int i = 0; i < count; i++) {
            target.add(code);
        }
    }

    /**
     * Convertit un code de layout en instance concrete de case.
     *
     * @param position index 1-based de la case
     * @param code identifiant de type (ennemi/objet)
     * @return case construite, ou null si code inconnu
     */
    private Cell buildCellFromCode(int position, String code) {
        // Protection: code absent -> aucune case speciale.
        if (code == null) {
            return null;
        }
        // Mapping centralise code -> type de case.
        switch (code.toUpperCase()) {
            case "ENEMY_DRAGON":
                return new EnemyCell(position, new Dragon());
            case "ENEMY_SORCERER":
                return new EnemyCell(position, new Sorcerer());
            case "ENEMY_GOBLIN":
                return new EnemyCell(position, new Goblin());
            default:
                WeaponCatalog.Entry weaponEntry = WeaponCatalog.byBoardCode(code);
                if (weaponEntry != null) {
                    return new ItemsCell(position, weaponEntry.toWeapon());
                }
                PotionCatalog.Entry potionEntry = PotionCatalog.byBoardCode(code);
                if (potionEntry != null) {
                    return new ItemsCell(position, potionEntry.toPotion());
                }
                // Code inconnu: on ignore la case pour garder la robustesse du build.
                return null;
        }
    }

    /**
     * Initialise un plateau court deterministe utile pour des tests manuels.
     */
    private void initTrainingBoard() {
        // Positions 2..4: rencontres ennemies progressives.
        cells.set(1, new EnemyCell(2, new Dragon()));
        cells.set(2, new EnemyCell(3, new Sorcerer()));
        cells.set(3, new EnemyCell(4, new Goblin()));

        // Positions 5..10: objets offensifs/defensifs de demonstration.
        cells.set(4, new ItemsCell(5, new Weapon("Pistolet 9 mm classique", 3)));
        cells.set(5, new ItemsCell(6, new Weapon("Carabine a plomb", 4)));
        cells.set(6, new ItemsCell(7, new Weapon("Pistolet silencieux", 5)));
        cells.set(7, new ItemsCell(8, new Weapon("Revolver de mercenaire", 6)));
        cells.set(8, new ItemsCell(9, PotionCatalog.byBoardCode("ITEM_STANDARD_POTION").toPotion()));
        cells.set(9, new ItemsCell(10, PotionCatalog.byBoardCode("ITEM_LARGE_POTION").toPotion()));
    }

    // Affiche toutes les cases pour debug ou verification rapide du layout.
    public void printCellsOverview() {
        System.out.println("\n--- Apercu du plateau (" + size + " cases) ---");
        for (Cell cell : cells) {
            System.out.println(cell);
        }
    }

    // Le depart est fixe sur la premiere case.
    public int getStartPosition() {
        return 1;
    }

    // Retourne la case a la position demandee, ou null hors bornes.
    public Cell getCellAt(int position) {
        if (!isInside(position)) {
            return null;
        }
        // Conversion position 1-based -> index 0-based.
        return cells.get(position - 1);
    }

    /**
     * Declenche l'interaction de la case courante avec le personnage.
     */
    public void interactAt(int position, Character character, Menu menu) {
        // Recupere la case courante selon la position joueur.
        Cell cell = getCellAt(position);
        if (cell == null) {
            // si c'est null alors aucune interaction
            return;
        }
        // Delegue l'effet a la case concrete.
        cell.interact(character, menu);
    }

    /**
     * Affiche un resume de la case courante (depart/arrivee/speciale).
     */
    public void checkCell(int position) {
        // Ignore les positions invalides.
        if (!isInside(position)) {
            return;
        }

        // Signale explicitement les cases structurantes du parcours.
        if (position == 1) {
            System.out.println("Case speciale: Depart");
        }
        if (position == size) {
            System.out.println("Case speciale: Arrivee");
        }

        // Affiche le detail uniquement pour les cases non vides.
        Cell currentCell = cells.get(position - 1);
        if (!(currentCell instanceof EmptyCell)) {
            System.out.println("Case speciale: " + currentCell);
        }
    }

    // Verifie si une position appartient aux bornes du plateau.
    public boolean isInside(int position) {
        return position >= 1 && position <= size;
    }

    // Retourne la taille courante du plateau.
    public int getSize() {
        return size;
    }

    // Modifie la taille puis regenere integralement les cases associees.
    public void setSize(int size) {
        this.size = size;
        initSpecialCells();
    }

    // Expose la liste interne des cases (utile pour tests/inspection).
    public ArrayList<Cell> getCells() {
        return cells;
    }

    @Override
    // Representation courte de l'etat du plateau.
    public String toString() {
        return "Board{" +
                "size=" + size +
                '}';
    }
}
