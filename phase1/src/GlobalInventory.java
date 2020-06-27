import java.util.ArrayList;

public class GlobalInventory {
    ArrayList<Item> itemList;

    //use default constructor in main method

    public void addItem(Item item){
        itemList.add(item);
    }
    public void removeItem(Item item){
        itemList.remove(item);
    }
    public boolean contains(Item item){
        return itemList.contains(item);
    }
    public ArrayList<Item> searchByOwner(String owner){
        ArrayList<Item> banana = new ArrayList<Item>(); //😀
        for (Item item : itemList){
            if (item.getOwnerName().equals(owner)){
                banana.add(item);
            }
        }
        return banana;
    }
    public ArrayList<Item> searchByItemName(String itemName){
        ArrayList<Item> banana = new ArrayList<Item>(); //😂
        for (Item item : itemList){
            if (item.getName().equals(itemName)){
                banana.add(item);
            }
        }
        return banana;



}
}
