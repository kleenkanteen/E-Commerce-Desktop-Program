//import junit4.*;

import F_entities.Item;
import F_entities.Trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class TradeTest {
    public static void main(String[] args) {
        Trade trade = new Trade("user", "user2", new ArrayList<Item>(), new ArrayList<Item>(), LocalDateTime.parse("2015-08-04T10:11:30")) {
            @Override
            public boolean getCompleted() {
                return false;
            }

            /**
             * Confirms a meeting for the user if they are part of a trade.
             * If they are not part of a trade then an exception is thrown!
             *
             * @param traderName   takes in a String that determines if they are part of the trade.
             * @param confirmation takes a boolean that determines if they confirmed the meeting.
             */
            @Override
            public void setConfirm(String traderName, boolean confirmation) {

            }
        };
        Trade trade1 = new Trade("user1", "user2", new ArrayList<Item>(), new ArrayList<Item>(), LocalDateTime.parse("2015-08-04T10:11:30")) {
            @Override
            public boolean getCompleted() {
                return false;
            }

            /**
             * Confirms a meeting for the user if they are part of a trade.
             * If they are not part of a trade then an exception is thrown!
             *
             * @param traderName   takes in a String that determines if they are part of the trade.
             * @param confirmation takes a boolean that determines if they confirmed the meeting.
             */
            @Override
            public void setConfirm(String traderName, boolean confirmation) {

            }
        };

        System.out.println(trade.equals(trade1));
    }

}
