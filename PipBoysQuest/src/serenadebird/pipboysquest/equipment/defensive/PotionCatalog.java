package serenadebird.pipboysquest.equipment.defensive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Catalogue central des potions du jeu.
 */
public final class PotionCatalog {
    /**
     * Entree immuable de catalogue.
     */
    public static final class Entry {
        private final String boardCode;
        private final String itemType;
        private final String name;
        private final int defense;
        private final int quantity;

        private Entry(String boardCode, String itemType, String name, int defense, int quantity) {
            this.boardCode = boardCode;
            this.itemType = itemType;
            this.name = name;
            this.defense = defense;
            this.quantity = quantity;
        }

        public String getBoardCode() {
            return boardCode;
        }

        public String getItemType() {
            return itemType;
        }

        public String getName() {
            return name;
        }

        public int getDefense() {
            return defense;
        }

        public int getQuantity() {
            return quantity;
        }

        public Potion toPotion() {
            if ("ITEM_STANDARD_POTION".equalsIgnoreCase(boardCode)) {
                return new StandardPotion();
            }
            if ("ITEM_LARGE_POTION".equalsIgnoreCase(boardCode)) {
                return new LargePotion();
            }
            return new Potion(name, defense);
        }
    }

    private static final List<Entry> POTION_ENTRIES;

    static {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry("ITEM_STANDARD_POTION", "StandardPotion", "Stimpack", 20, 6));
        values.add(new Entry("ITEM_LARGE_POTION", "LargePotion", "Super Stimpack", 40, 2));
        POTION_ENTRIES = Collections.unmodifiableList(values);
    }

    private PotionCatalog() {
    }

    public static List<Entry> entries() {
        return POTION_ENTRIES;
    }

    public static Entry byBoardCode(String boardCode) {
        if (boardCode == null) {
            return null;
        }
        for (Entry entry : POTION_ENTRIES) {
            if (entry.getBoardCode().equalsIgnoreCase(boardCode)) {
                return entry;
            }
        }
        return null;
    }
}

