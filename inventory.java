public class inventory {

    private String itemName;
    private int stockLevel;

    public inventory(String itemName, int stockLevel) {
        this.itemName = itemName;
        this.stockLevel = stockLevel;
    }

    public String getItemName() {
        return itemName;
    }

    public int getStockLevel() {
        return stockLevel;
    }
}
