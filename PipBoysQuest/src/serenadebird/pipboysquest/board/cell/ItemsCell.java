package serenadebird.pipboysquest.board.cell;

public class ItemsCell extends Cell {
    private String itemType;
    private String itemName;
    private int itemValue;

    public ItemsCell(int position, String itemType, String itemName, int itemValue) {
        super(position);
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public String getItemName() {return itemName;}
    public void SetItemName(String itemName) {this.itemName = itemName;}

    public int getItemValue() {return itemValue;}
    public void SetItemValue(int itemValue) {this.itemValue = itemValue;}

    public String getItemType() {return itemType;}
    public void setItemType(String itemType) {this.itemType = itemType;}

    @Override
    public String toString() {
        return "Case " + getPosition() + " : objet a looter (" + itemType + " - " + itemName + " +" + itemValue + ")";
    }

}
