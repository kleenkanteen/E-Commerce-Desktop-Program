import entities.*;
import java.util.*;
//import static org.junit.Assert.*;
public class UserTest {
    public static void main(String[] args){
        User u1 = new User("a", "a");
        User u2 = new User("b", "a");
        User u3 = new User("c", "a");
        User u4 = new User("d", "a");
        User u5 = new User("e", "a");

        Item i1 = new Item("1", "a", "a");
        //Item i2 = new Item("2", "a", "a");

        ArrayList<Item> temp1 = new ArrayList<>();
        ArrayList<Item> temp2 = new ArrayList<>();
        temp1.add(i1);

        Calendar c1 = Calendar.getInstance();

        PermTrade p1 = new PermTrade(u1, u2, temp1, temp2, c1);
        PermTrade p2 = new PermTrade(u1, u3, temp1, temp2, c1);
        PermTrade p3 = new PermTrade(u1, u4, temp1, temp2, c1);
        PermTrade p4 = new PermTrade(u1, u2, temp1, temp2, c1);
        PermTrade p5 = new PermTrade(u1, u5, temp1, temp2, c1);
        PermTrade p6 = new PermTrade(u1, u4, temp1, temp2, c1);
        PermTrade p7 = new PermTrade(u1, u5, temp1, temp2, c1);

        u1.addTradeHistory(p1);
        u1.addTradeHistory(p2);
        u1.addTradeHistory(p3);
        u1.addTradeHistory(p4);
        u1.addTradeHistory(p5);
        u1.addTradeHistory(p6);
        u1.addTradeHistory(p7);

        User[] users = u1.getFrequentTradingPartners();

        for(User u: users){
            System.out.println(u);
        }

        System.out.println(u1.getBorrowedTimes());
        System.out.println(u2.getLendTimes());
    }
}
