package C_controllers;

import D_presenters.MessageReplyMenu;
import F_entities.*;
import G_exceptions.UserFrozenException;
import E_use_cases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.time.*;
import java.time.format.*;

public class UserMessageReplySystem {
    private UserManager um;
    private GlobalInventoryManager gi;
    private TradeManager tm;
    private String accountUsername;
    private MessageReplyMenu mm;

    /**
     * Class constructor.
     * Create a new UserMessageReplySystem that controls and allows the user to reply to their messages
     * @param accountUsername the username of the currently logged in User
     * @param um the user manager of the system
     * @param tm the trade manager of the system
     * @param gi the global inventory manager of the system
     */
    public UserMessageReplySystem(UserManager um, GlobalInventoryManager gi, TradeManager tm, String accountUsername){
        this.um = um;
        this.gi = gi;
        this.tm = tm;
        this.accountUsername = accountUsername;
        mm = new MessageReplyMenu();
    }

    /**
     * Interacts with the user to allow them to respond and reply to their messages
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Message> messages = um.getUserMessages(accountUsername);
        if(messages.size() == 0){
            mm.printNoMessages();
            mm.printExit();
            return;
        }
        try {
            String input = "";
            while(true) {
                mm.printMenuPrompt(messages.size());
                input = br.readLine();
                if(input.equals("2"))return;
                else if(input.equals("1")) break;
                else mm.printInvalidInput();
            }

            final ArrayList<Message> loopingMessages =  new ArrayList<Message>(messages);
            for(Message m: loopingMessages){
                if(m instanceof TradeRequestMessage){
                    if(!TradeRequestMessageResponse((TradeRequestMessage) m, messages, br))return;
                }
                else {
                    if (!ContentMessageResponse(m, messages, br)) return;
                }
            }
        }catch(IOException e){
            mm.printInvalidInput();
        }finally {
            um.setUserMessages(accountUsername, messages);
            mm.printExit();
        }
    }
    private void TradeRequestMessageEdit(TradeRequestMessage m, BufferedReader br){
        TradeRequest t = m.getTradeContent();
        String username = m.getSender();
        TradeRequestManager tempTRM = new TradeRequestManager(t);

        if(!tempTRM.canEdit(accountUsername)&&!tempTRM.canEdit(username)){
            mm.tradeRequestCancel();
            createMessage(username, "Your trade request:"+t.toString()+"\n is cancelled due to too much edits");
        }
        else{
            String input = "";
            LocalDateTime time = t.getDate();
            String place = t.getPlace();
            try {
                while (true) {
                    mm.printEditTradeRequestPrompt(t);
                    input = br.readLine();
                    if(input.equals("2")||input.equals("3")){
                        while (true) {
                            try {
                                mm.changeDatePrompt(t.getDate());
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                time = LocalDateTime.parse(br.readLine(), dtf);
                                break;
                            } catch (DateTimeParseException e) {
                                mm.wrongFormat();
                            } catch (IOException e) {
                                mm.printErrorOccurred();
                            }
                        }
                    }
                    if(input.equals("1")||input.equals("3")){
                        while (true) {
                            try {
                                mm.changePlacePrompt(place);
                                place = br.readLine();
                                break;
                            } catch (IOException e) {
                                mm.printErrorOccurred();
                            }
                        }
                    }
                    if(input.equals("1")||input.equals("2")||input.equals("3"))break;
                }
                tempTRM.setDateAndPlace(accountUsername, time, place);
                createMessage(username, "Your trade request has been edited", tempTRM.getTradeRequest(),
                        accountUsername);
            }
            catch(IOException e){
                mm.printErrorOccurred();
            }
        }
    }
    private boolean TradeRequestMessageResponse(TradeRequestMessage m, ArrayList<Message> messages, BufferedReader br)
            throws IOException{
        mm.printDecisionMessagePrompt(m);
        TradeRequest t = m.getTradeContent();
        String username = m.getSender();
        TradeRequestManager temp = new TradeRequestManager(t);

        if(!temp.canEdit(accountUsername)&&!temp.canEdit(username))mm.tradeRequestWarning();
        System.out.println("");
        while (true) {
            String input = br.readLine();
            switch (input){
                case "a":
                    return true;
                case "b":
                    return false;
                case "1":
                    if(cannotTrade(t.getUserA(),t.getItemA())||cannotTrade(t.getUserB(), t.getItemB())){
                        mm.printCannotTradePrompt();
                        while(true) {
                            input = br.readLine();
                            if(input.equals("2"))return true;
                            else if(input.equals("1")) {
                                messages.remove(m);
                                createMessage(username, "You or the other trader cannot create a new trade " +
                                        "at this time or the items involved or not for trade at this time. The" +
                                        "Other trader has choosed to delete this trade request.\n"+
                                        "Trade Request: "+t.toString());
                                return true;
                            }
                            else mm.printInvalidInput();
                        }
                    }
                    messages.remove(m);
                    Trade trade = temp.setConfirmation(accountUsername);
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
                    return true;
                case "2":
                    messages.remove(m);
                    createMessage(username, "Your trade request:"+t.toString()+"\n is rejected by "+
                            accountUsername);
                    return true;
                case "3":
                    messages.remove(m);
                    TradeRequestMessageEdit(m, br);
                    return true;
                default:
                    mm.printInvalidInput();
            }
        }
    }
    private boolean ContentMessageResponse(Message m, ArrayList<Message> messages,
                                           BufferedReader br) throws IOException {
        while (true) {
            mm.printContentMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "1":
                    messages.remove(m);
                    return true;
                case "2":
                    return true;
                case "3":
                    return false;
                default:
                    mm.printInvalidInput();
            }
        }
    }
    private boolean cannotTrade(String username, ArrayList<Item> userItem){
        try{
            if(!um.getCanTrade(username, tm.getBorrowedTimes(username), tm.getLendTimes(username),
                    tm.getIncompleteTimes(username), tm.numberOfTradesCreatedThisWeek(username))) return true;
        }catch(UserFrozenException e){
            return true;
        }
        for(Item i: userItem){
            if(!gi.contains(i))return true;
            if(!um.getUserInventory(username).contains(i)) return true;
        }
        return false;
    }
    private void createMessage(String username, String content){
        Message reply = new Message(content);
        um.addUserMessage(username, reply);
    }
    private void createMessage(String username, String content, TradeRequest t, String sender){
        Message reply = new TradeRequestMessage(content, t, sender);
        um.addUserMessage(username, reply);
    }
}
