import entities.*;
import java.util.*;
public class UserTest {
    public static void main(String[] args){
        User u1 = new User("a", "a");
        User u2 = new User("b", "a");
        User u3 = new User("c", "a");
        User u4 = new User("d", "a");

        Item i1 = new Item("1", "a", "a");
        //Item i2 = new Item("2", "a", "a");

        ArrayList<Item> temp = new ArrayList<>();
        temp.add(i1);

        Calendar c1 = Calendar.getInstance();

        PermTrade p1 = new PermTrade(u1, u2, temp, null, c1);
        PermTrade p2 = new PermTrade(u1, u3, temp, null, c1);
        PermTrade p3 = new PermTrade(u1, u4, temp, null, c1);
        PermTrade p4 = new PermTrade(u1, u2, temp, null, c1);
        PermTrade p5 = new PermTrade(u1, u2, temp, null, c1);

        u1.addTradeHistory(p1);
        u1.addTradeHistory(p2);
        u1.addTradeHistory(p3);
        u1.addTradeHistory(p4);
        u1.addTradeHistory(p5);

        User[] users = u1.getFrequentTradingPartners();

        for(User u: users){
            System.out.println(u);
        }
    }
}
