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
import serenadebird.pipboysquest.equipment.defensive.LargePotion;
import serenadebird.pipboysquest.equipment.defensive.StandardPotion;
import serenadebird.pipboysquest.equipment.offensive.Fireball;
import serenadebird.pipboysquest.equipment.offensive.Lightning;
import serenadebird.pipboysquest.equipment.offensive.Mace;
import serenadebird.pipboysquest.equipment.offensive.Sword;

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
    private int size = 64;
    private final ArrayList<Cell> cells = new ArrayList<>();
    private DatabaseManager db;
    private final Random random = new Random();

    public Board() {
        initSpecialCells();
    }

    public Board(int size) {
        this.size = size;
        initSpecialCells();
    }

    public Board(int size, DatabaseManager db) {
        this.size = size;
        this.db = db;
        initSpecialCells();
    }

    private void initSpecialCells() {
        cells.clear();
        for (int i = 1; i <= size; i++) {
            cells.add(new EmptyCell(i));
        }

        if (size == 10) {
            initTrainingBoard();
            return;
        }

        if (size >= 64) {
            initRandomFullBoard();
            return;
        }

        if (size >= 3) {
            cells.set(2, new EnemyCell(3, new Goblin()));
        }
    }

    /**
     * Genere un plateau 64 cases aleatoire en conservant les quantites fixes.
     */
    private void initRandomFullBoard() {
        List<String> codes = new ArrayList<>();
        addCodes(codes, "ENEMY_DRAGON", 4);
        addCodes(codes, "ENEMY_SORCERER", 10);
        addCodes(codes, "ENEMY_GOBLIN", 10);

        addCodes(codes, "ITEM_MACE", 5);
        addCodes(codes, "ITEM_SWORD", 4);
        addCodes(codes, "ITEM_LIGHTNING", 5);
        addCodes(codes, "ITEM_FIREBALL", 2);
        addCodes(codes, "ITEM_STANDARD_POTION", 6);
        addCodes(codes, "ITEM_LARGE_POTION", 2);

        List<Integer> positions = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            positions.add(i);
        }

        Collections.shuffle(codes, random);
        Collections.shuffle(positions, random);

        Map<Integer, String> generatedLayout = new HashMap<>();
        for (int i = 0; i < codes.size(); i++) {
            int position = positions.get(i);
            String code = codes.get(i);
            Cell cell = buildCellFromCode(position, code);
            if (cell != null) {
                cells.set(position - 1, cell);
                generatedLayout.put(position, code);
            }
        }

        if (db != null) {
            db.replaceBoardLayout(generatedLayout);
        }
    }

    private void addCodes(List<String> target, String code, int count) {
        for (int i = 0; i < count; i++) {
            target.add(code);
        }
    }

    private Cell buildCellFromCode(int position, String code) {
        if (code == null) {
            return null;
        }
        switch (code.toUpperCase()) {
            case "ENEMY_DRAGON":
                return new EnemyCell(position, new Dragon());
            case "ENEMY_SORCERER":
                return new EnemyCell(position, new Sorcerer());
            case "ENEMY_GOBLIN":
                return new EnemyCell(position, new Goblin());
            case "ITEM_MACE":
                return new ItemsCell(position, new Mace());
            case "ITEM_SWORD":
                return new ItemsCell(position, new Sword());
            case "ITEM_LIGHTNING":
                return new ItemsCell(position, new Lightning());
            case "ITEM_FIREBALL":
                return new ItemsCell(position, new Fireball());
            case "ITEM_STANDARD_POTION":
                return new ItemsCell(position, new StandardPotion());
            case "ITEM_LARGE_POTION":
                return new ItemsCell(position, new LargePotion());
            default:
                return null;
        }
    }

    private void initTrainingBoard() {
        cells.set(1, new EnemyCell(2, new Dragon()));
        cells.set(2, new EnemyCell(3, new Sorcerer()));
        cells.set(3, new EnemyCell(4, new Goblin()));

        cells.set(4, new ItemsCell(5, new Mace()));
        cells.set(5, new ItemsCell(6, new Sword()));
        cells.set(6, new ItemsCell(7, new Lightning()));
        cells.set(7, new ItemsCell(8, new Fireball()));
        cells.set(8, new ItemsCell(9, new StandardPotion()));
        cells.set(9, new ItemsCell(10, new LargePotion()));
    }

    public void printCellsOverview() {
        System.out.println("\n--- Apercu du plateau (" + size + " cases) ---");
        for (Cell cell : cells) {
            System.out.println(cell);
        }
    }

    public int getStartPosition() {
        return 1;
    }

    public Cell getCellAt(int position) {
        if (!isInside(position)) {
            return null;
        }
        return cells.get(position - 1);
    }

    public void interactAt(int position, Character character) {
        Cell cell = getCellAt(position);
        if (cell == null) {
            return;
        }
        cell.interact(character);
    }

    public void checkCell(int position) {
        if (!isInside(position)) {
            return;
        }

        if (position == 1) {
            System.out.println("Case speciale: Depart");
        }
        if (position == size) {
            System.out.println("Case speciale: Arrivee");
        }

        Cell currentCell = cells.get(position - 1);
        if (!(currentCell instanceof EmptyCell)) {
            System.out.println("Case speciale: " + currentCell);
        }
    }

    public boolean isInside(int position) {
        return position >= 1 && position <= size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        initSpecialCells();
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "Board{" +
                "size=" + size +
                '}';
    }
}
