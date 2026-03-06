package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.equipment.DefensiveEquipment;
import serenadebird.pipboysquest.equipment.OffensiveEquipment;

public class ItemsCell extends Cell {
    private String itemType;
    private String itemName;
    private int itemValue;
    private Object item;

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
}
