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

        //Initial Menu
        if(messages.size() == 0){
            mm.printNoMessages();
            mm.printExit();
            return;
        }
        try {
            String input = "";
            do {
                mm.printMenuPrompt(messages.size());
                input = br.readLine();
                if(input.equals("2"))return;
                else if(!input.equals("1")) mm.printInvalidInput();
            }while(!input.equals("1"));

            //Going through all the messages the user have
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
            //Exiting the user from the menu do to an error or that the user has exited
            um.setUserMessages(accountUsername, messages);
            mm.printExit();
        }
    }

    //Allow the user to edit a trade request
    private void TradeRequestMessageEdit(TradeRequestMessage m, BufferedReader br){
        TradeRequest t = m.getTradeContent();
        String username = m.getSender();
        TradeRequestManager tempTRM = new TradeRequestManager(t);

        //warning the user that their trade request is cancelled due to too much edits
        if(!tempTRM.canEdit(accountUsername)&&!tempTRM.canEdit(username)){
            mm.tradeRequestCancel();
            um.createUserMessage(username, "Your trade request:"+t.toString()+"\n is cancelled due to too much edits");
        }
        //Allow the user to edit a trade request
        else{
            String input = "";
            LocalDateTime time = t.getDate();
            String place = t.getPlace();
            try {
                while (!input.equals("1")&&!input.equals("2")&&!input.equals("3")) {
                    mm.printEditTradeRequestPrompt(t);
                    input = br.readLine();
                    boolean valid = false;
                    //Editing the date
                    if(input.equals("2")||input.equals("3")){
                        do{
                            try {
                                mm.changeDatePrompt(t.getDate());
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                time = LocalDateTime.parse(br.readLine(), dtf);
                                if(!time.isBefore(LocalDateTime.now()))  valid = true;
                                else{
                                    mm.wrongDate();
                                }
                            } catch (DateTimeParseException e) {
                                mm.wrongFormat();
                            } catch (IOException e) {
                                mm.printErrorOccurred();
                            }
                        }while (!valid);
                    }
                    //Editing the place
                    if(input.equals("1")||input.equals("3")){
                        valid = false;
                        do {
                            try {
                                mm.changePlacePrompt(place);
                                place = br.readLine();
                                valid = true;
                            } catch (IOException e) {
                                mm.printErrorOccurred();
                            }
                        }while (!valid);
                    }
                }
                //Setting the new date/place that the user edit
                tempTRM.setDateAndPlace(accountUsername, time, place);
                //Sent the new trade request to other trader
                um.createAndAddNewTradeRequestMessage(username, "Your trade request has been edited",
                        tempTRM.getTradeRequest(), accountUsername);
                mm.success();
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

        //Warn the user if their trade request cannot be edit anymore
        if(!temp.canEdit(accountUsername)&&!temp.canEdit(username))mm.tradeRequestWarning();
        boolean done = false;
        do {
            String input = br.readLine();
            switch (input){
                case "a":
                    done = true;
                    break;
                case "b":
                    return false;
                case "1":
                    //Check if the trade can be created
                    if(cannotTrade(t.getUserA(),t.getItemA())||cannotTrade(t.getUserB(), t.getItemB())){
                        //Tell the user that their trade cannot be created at this time
                        mm.printCannotTradePrompt();
                        do {
                            input = br.readLine();
                            if(input.equals("2"))done = true;
                            else if(input.equals("1")) {
                                messages.remove(m);
                                //Tell the other trader that the trade could not be created at this time
                                um.createUserMessage(username, "You or the other trader cannot create a new trade " +
                                        "at this time or the items involved or not for trade at this time. The" +
                                        "Other trader has choosed to delete this trade request.\n"+
                                        "Trade Request: "+t.toString());
                                done = true;
                            }
                            else mm.printInvalidInput();
                        }while(!done);
                        return true;
                    }
                    //Confirming the trade
                    messages.remove(m);
                    Trade trade = temp.setConfirmation(accountUsername);
                    //Add trade to both user's trade history
                    System.out.println(trade);
                    tm.addTrade(trade);

                    //Removing the items from the GI and personal inventory
                    ArrayList<Item> list = new ArrayList<>(trade.getTraderAItemsToTrade());
                    for(Item i:list) {
                        um.removeItemFromUserInventory(trade.getTraderA(), i.getItemID());
                        gi.removeItem(i.getItemID());
                    }
                    list = new ArrayList<>(trade.getTraderBItemsToTrade());
                    for(Item i:list) {
                        um.removeItemFromUserInventory(trade.getTraderB(), i.getItemID());
                        gi.removeItem(i.getItemID());
                    }
                    mm.success();
                    done = true;
                    break;
                case "2":
                    //Removing and informing the other trade that the request is rejected
                    messages.remove(m);
                    um.createUserMessage(username, "Your trade request:"+t.toString()+"\n is rejected by "+
                            accountUsername);
                    mm.success();
                    done = true;
                    break;
                case "3":
                    messages.remove(m);
                    TradeRequestMessageEdit(m, br);
                    done = true;
                    break;
                default:
                    mm.printInvalidInput();
            }
        }while(!done);
        return true;
    }
    private boolean ContentMessageResponse(Message m, ArrayList<Message> messages,
                                           BufferedReader br) throws IOException {
        boolean done = false;
        do {
            mm.printContentMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "1":
                    messages.remove(m);
                    done = true;
                    break;
                case "2":
                    done = true;
                    break;
                case "3":
                    return false;
                default:
                    mm.printInvalidInput();
            }
        }while(!done);
        return true;
    }
    private boolean cannotTrade(String username, ArrayList<Item> userItem){
        //Checking if the user can trade
        try{
            if(!um.getCanTrade(username, tm.getBorrowedTimes(username), tm.getLendTimes(username),
                    tm.getIncompleteTimes(username), tm.numberOfTradesCreatedThisWeek(username))) return true;
        }catch(UserFrozenException e){
            return true;
        }
        //Checking if the items in the trade is valid aka in the personal inventory and GI
        for(Item i: userItem){
            if(!gi.contains(i))return true;
            boolean contain = false;
            for(Item j: userItem){
                if(i.isEqual(j))contain = true;
            }
            if(!contain)return true;
        }
        return false;
    }
}
