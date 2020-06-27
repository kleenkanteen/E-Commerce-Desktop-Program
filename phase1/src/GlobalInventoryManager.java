import java.util.ArrayList;
import java.util.HashMap;

public class GlobalInventoryManager {
    GlobalInventory gI;
    GlobalInventoryManager(GlobalInventory gI){
        this.gI = gI;
    }

    public void addItem(Item item){
        gI.addItem(item);
    }

    public void removeItem(Item item){
        gI.removeItem(item);
    }

    public ArrayList<Item> searchItem(String itemName){
        return gI.searchByItemName(itemName);
    }

    public ArrayList<Item> generatePage(int pageNumber){
        ArrayList<Item> itemList = new ArrayList<Item>();
        for (int i = (pageNumber - 1) * 10; i < pageNumber * 10 ; i++){
            itemList.add(gI.getItem(i));

        }
        return itemList;

    }

    public ArrayList<Item> searchUser(String UserName){
        return gI.searchByOwner(UserName);
    }


}
