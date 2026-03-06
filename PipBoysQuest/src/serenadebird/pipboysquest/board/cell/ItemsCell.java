package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.character.Warrior;
import serenadebird.pipboysquest.character.Wizard;
import serenadebird.pipboysquest.equipment.DefensiveEquipment;
import serenadebird.pipboysquest.equipment.OffensiveEquipment;
import serenadebird.pipboysquest.equipment.offensive.Spell;
import serenadebird.pipboysquest.equipment.offensive.Weapon;

public class ItemsCell extends Cell {
    private String itemType;
    private String itemName;
    private int itemValue;
    private Object item;
    private boolean looted;

    public ItemsCell(int position, String itemType, String itemName, int itemValue) {
        super(position);
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public ItemsCell(int position, OffensiveEquipment item) {
        super(position);
        this.item = item;
        this.itemType = item.getType();
        this.itemName = item.getName();
        this.itemValue = item.getAttackLevel();
    }

    public ItemsCell(int position, DefensiveEquipment item) {
        super(position);
        this.item = item;
        this.itemType = item.getType();
        this.itemName = item.getName();
        this.itemValue = item.getDefenseLevel();
    }

    public String getItemName() {return itemName;}
    public void setItemName(String itemName) {this.itemName = itemName;}

    public int getItemValue() {return itemValue;}
    public void setItemValue(int itemValue) {this.itemValue = itemValue;}

    public String getItemType() {return itemType;}
    public void setItemType(String itemType) {this.itemType = itemType;}

    public Object getItem() { return item; }
    public void setItem(Object item) { this.item = item; }

    @Override
    public String toString() {
        if (item != null) {
            return "Case " + getPosition() + " : loot Fallout -> " + item;
        }
        return "Case " + getPosition() + " : objet a looter (" + itemType + " - " + itemName + " +" + itemValue + ")";
    }

    @Override
    public void interact(Character character) {
        if (looted) {
            System.out.println("Cette caisse a deja ete fouillee.");
            return;
        }

        if (item instanceof OffensiveEquipment) {
            handleOffensiveLoot(character, (OffensiveEquipment) item);
            return;
        }
        if (item instanceof DefensiveEquipment) {
            handleDefensiveLoot(character, (DefensiveEquipment) item);
            return;
        }

        // Fallback legacy si la case a ete creee avec l'ancien constructeur String.
        System.out.println(character.getName() + " trouve " + itemName + ".");
        if ("POTION".equalsIgnoreCase(itemType)) {
            character.increaseHealthWithCap(itemValue);
            looted = true;
            return;
        }
        if ("WEAPON".equalsIgnoreCase(itemType) && character instanceof Warrior) {
            character.increaseAttackWithCap(itemValue);
            looted = true;
            return;
        }
        if ("SPELL".equalsIgnoreCase(itemType) && character instanceof Wizard) {
            character.increaseAttackWithCap(itemValue);
            looted = true;
            return;
        }
        System.out.println("Loot incompatible avec votre classe.");
    }

    private void handleOffensiveLoot(Character character, OffensiveEquipment equipment) {
        boolean canTake = (equipment instanceof Weapon && character instanceof Warrior)
                || (equipment instanceof Spell && character instanceof Wizard);

        if (!canTake) {
            System.out.println("Loot incompatible: " + character.getType() + " ne peut pas utiliser " + equipment.getType() + ".");
            return;
        }

        int oldAttack = character.getAttackStrength();
        character.setOffensiveEquipment(equipment);
        character.increaseAttackWithCap(equipment.getAttackLevel());
        looted = true;

        System.out.println(character.getName() + " equipe " + equipment.getName() + ".");
        System.out.println("Force: " + oldAttack + " -> " + character.getAttackStrength());
    }

    private void handleDefensiveLoot(Character character, DefensiveEquipment equipment) {
        int oldHealth = character.getHealthLevel();
        character.setDefensiveEquipment(equipment);
        character.increaseHealthWithCap(equipment.getDefenseLevel());
        looted = true;

        System.out.println(character.getName() + " utilise " + equipment.getName() + ".");
        System.out.println("Vie: " + oldHealth + " -> " + character.getHealthLevel());
    }
}
