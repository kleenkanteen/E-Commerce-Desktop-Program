package test;
import entities.GlobalInventory;
import entities.Item;
import uses_cases.GlobalInventoryManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
public class GlobalInventoryTest {


        public static void main(String[] args) throws InvalidItemException {

            Item item1 = new Item("String name1", "ownerName1",
                    "description1"
);
            Item item2 = new Item("String name2", "ownerName2",
                    "description2"
                   );
            Item item3 = new Item("String name3", "ownerName3",
                    "description3"
            );
            Item item4 = new Item("String name4", "ownerName4",
                    "description4"
            );
            Item item5 = new Item("String name5", "ownerName5",
                    "description5"
            );
            Item item6 = new Item("String name6", "ownerName6",
                    "description6"
            );
            Item item7 = new Item("String name7", "ownerName7",
                    "description7"
            );
            Item item8 = new Item("String name8", "ownerName8",
                    "description8"
            );
            Item item9 = new Item("String name9", "ownerName9",
                    "description9"
            );
            Item item10 = new Item("String name10", "ownerName10",
                    "description10"
            );
            Item item11 = new Item("String name11", "ownerName11",
                    "description11"
            );
            Item item12 = new Item("String name12", "ownerName12",
                    "description12"
            );
            Item item13 = new Item("String name13", "ownerName13",
                    "description13"
            );
            Item item14 = new Item("String name14", "ownerName14",
                    "description14"
            );


            GlobalInventoryManager f =
                    new GlobalInventoryManager("/group_0147/phase1/src" +
                            "/ser_file_infos/serializedGlobalInventory.ser");
            f.addItemToHashMap(item1);
            f.addItemToHashMap(item2);
            f.removeItem(item1.getItemID());
            System.out.println(f.generatePage(1));
            f.addItemToHashMap(item3);
            System.out.println(f.getGI().getItemIdCollection());








    }


}
