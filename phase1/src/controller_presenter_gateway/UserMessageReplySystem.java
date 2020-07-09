package controller_presenter_gateway;

import entities.*;
import use_cases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.util.ArrayList;
import java.util.*;
import java.text.*;

public class UserMessageReplySystem {
    private ArrayList<Message> messages;
    private UserManager um;
    private GlobalInventoryManager gi;
    private GlobalWishlistManager gw;
    private TradeManager tm;
    private String accountUsername;
    private MessageReplyMenu mm;

    public UserMessageReplySystem(UserManager um, GlobalInventoryManager gi, GlobalWishlistManager gw,
                                  TradeManager tm,
                                  String accountUsername){
        this.um = um;
        this.gi = gi;
        this.gw = gw;
        this.tm = tm;
        this.accountUsername = accountUsername;
        this.messages = um.getUserMessages(accountUsername);
        mm = new MessageReplyMenu(messages);
    }
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = null;
            while(true) {
                input = br.readLine();
                if(input.equals("exit"))return;
                if(input.equals("view"))break;
            }

            for(int i = 0; i < messages.size(); i++){
                Message m = messages.get(i);
                mm.printMessage(i);

                if(m instanceof TradeRequestMessage){
                    if(!TradeRequestMessageResponse((TradeRequestMessage) m, br))return;
                }
                else {
                    if (!ContentMessageResponse(m, br)) return;
                }
            }
            br.close();
        }catch(IOException e){
            System.out.println("Something went wrong");
        }
    }
    private void TradeRequestMessageEdit(TradeRequestMessage m, BufferedReader br){
        TradeRequest t = m.getTradeContent();
        String username = m.getSender();
        messages.remove(m);

        TradeRequestManager temp = new TradeRequestManager(t);
        if(!temp.canEdit(accountUsername)&&!temp.canEdit(username)){
            System.out.println("Trade request cancelled");

            Message reply = new Message("Your trade request:"+t+"\n is cancelled due to too much edits");
            ArrayList<Message> tempMessage = um.getUserMessages(username);
            tempMessage.add(reply);
            um.setUserMessages(username, tempMessage);
        }
        else{
            String input = "";
            while(true){
                try {
                    System.out.println("Enter the new date in the format yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    input = br.readLine();
                    if(input.length()!=10)System.out.println("wrong format");
                    else {
                        cal.setTime(sdf.parse(input));
                        temp.setDate(accountUsername, cal);
                        break;
                    }
                }
                catch (ParseException e){
                    System.out.println("wrong format");
                }
                catch (IOException e){
                    System.out.println("try again");
                }
            }
            while(true){
                try {
                    System.out.println("Enter the new place: ");
                    input = br.readLine();
                    temp.setPlace(accountUsername, input);
                    break;
                }
                catch (IOException e){
                    System.out.println("try again");
                }
            }
            TradeRequestMessage reply = new TradeRequestMessage("Your trade request has been edited", t,
                    accountUsername);
            ArrayList<Message> tempMessage = um.getUserMessages(username);
            tempMessage.add(reply);
            um.setUserMessages(username, tempMessage);
        }
    }
    private boolean TradeRequestMessageResponse(TradeRequestMessage m, BufferedReader br) throws IOException{
        mm.printDecisionMessagePrompt();
        TradeRequest t = m.getTradeContent();
        String username = m.getSender();

        TradeRequestManager temp = new TradeRequestManager(t);

        if(!temp.canEdit(accountUsername)&&!temp.canEdit(username))System.out.println("Warning, if the max number " +
                "of edits for both traders are reach, selecting edit means " +
                    "you will cancel this trade request");
        System.out.println("");
        while (true) {
            String input = br.readLine();
            if(input.equals("exit")) return false;
            if(input.equals("next")) break;
            if(input.equals("1")){
                messages.remove(m);
                Trade trade = temp.setConfirmation(accountUsername);
                //TODO maybe check if the users can trade N/A
                //Add trade to both user's trade history
                tm.addTrade(trade);

                //Removing the items from the GI and personal inventory and wishlist
                for(Item i:trade.getTraderAItemsToTrade()) {
                    um.removeItemFromUserInventory(trade.getTraderA(), i.getItemID());

                    gi.removeItem(i.getItemID());
                }
                for(Item i:trade.getTraderBItemsToTrade()) {
                    um.removeItemFromUserInventory(trade.getTraderB(), i.getItemID());

                    gi.removeItem(i.getItemID());
                }
                break;
            }
            else if(input.equals("2")){
                t = m.getTradeContent();
                username = m.getSender();
                messages.remove(m);

                Message reply = new Message("Your trade request:"+t+"\n is rejected by "+accountUsername);
                ArrayList<Message> tempMessage = um.getUserMessages(username);
                tempMessage.add(reply);
                um.setUserMessages(username, tempMessage);
                break;
            }
            else if(input.equals("3")){
                TradeRequestMessageEdit(m, br);
                break;
            }
        }
        return true;
    }
    private boolean ContentMessageResponse(Message m, BufferedReader br) throws IOException {
        mm.printContentMessagePrompt();
        while (true) {
            String input = br.readLine();
            if(input.equals("exit")) return false;
            if(input.equals("delete")) {
                messages.remove(m);
                return true;
            }
            if(input.equals("next"))return true;
        }
    }
}
