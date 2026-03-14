package serenadebird.pipboysquest.equipment.offensive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Catalogue central des armes du jeu.
 */
public final class WeaponCatalog {
    /**
     * Entree immuable de catalogue.
     */
    public static final class Entry {
        private final String boardCode;
        private final String name;
        private final int attack;
        private final int quantity;

        private Entry(String boardCode, String name, int attack, int quantity) {
            this.boardCode = boardCode;
            this.name = name;
            this.attack = attack;
            this.quantity = quantity;
        }

        public String getBoardCode() {
            return boardCode;
        }

        public String getName() {
            return name;
        }

        public int getAttack() {
            return attack;
        }

        public int getQuantity() {
            return quantity;
        }

        public Weapon toWeapon() {
            return new Weapon(name, attack);
        }
    }

    private static final List<Entry> OFFENSIVE_ENTRIES;

    static {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry("ITEM_PISTOLET_9MM", "Pistolet 9 mm classique", 3, 3));
        values.add(new Entry("ITEM_CARABINE_PLOMB", "Carabine a plomb", 4, 3));
        values.add(new Entry("ITEM_PISTOLET_SILENCIEUX", "Pistolet silencieux", 5, 3));
        values.add(new Entry("ITEM_REVOLVER_MERCENAIRE", "Revolver de mercenaire", 6, 3));
        values.add(new Entry("ITEM_HACHE_BARBARE", "Hache de barbare", 7, 2));
        values.add(new Entry("ITEM_CARABINE_LEVIER", "Carabine a levier", 11, 2));
        values.add(new Entry("ITEM_LANCE_FERRAILLE", "Lance-ferraille", 13, 2));
        values.add(new Entry("ITEM_FUSIL_COMBAT_TACTIQUE", "Fusil de combat tactique", 18, 2));
        values.add(new Entry("ITEM_FUSIL_GAUSS", "Fusil Gauss", 26, 1));
        values.add(new Entry("ITEM_FAT_MAN", "Fat Man", 28, 1));
        values.add(new Entry("ITEM_FINAL_JUDGMENT", "Final Judgment", 30, 1));
        OFFENSIVE_ENTRIES = Collections.unmodifiableList(values);
    }

    private WeaponCatalog() {
    }

    public static List<Entry> entries() {
        return OFFENSIVE_ENTRIES;
    }

    public static Entry byBoardCode(String boardCode) {
        if (boardCode == null) {
            return null;
        }
        for (Entry entry : OFFENSIVE_ENTRIES) {
            if (entry.getBoardCode().equalsIgnoreCase(boardCode)) {
                return entry;
            }
        }
        return null;
    }
}


