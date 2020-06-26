import java.util.ArrayList;

public class GlobalInventory {
    ArrayList<Item> itemList;
    public void addItem(Item item){
        itemList.add(item);
    }
    public void removeItem(Item item){
        itemList.remove(item);
    }
    public boolean contains(Item item){
        return itemList.contains(item);
    }






}
