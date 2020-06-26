import java.util.ArrayList;

public class GlobalInventory {
    ArrayList<Item> itemList;
    public void addItem(Item item){
        itemList.add(item);
    }
    public void removeItem(Item item){
        itemList.remove(item);
    }
    //public void returnTenItems(){} I am thinking if we should put it into UserManager




}
