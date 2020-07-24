package controllers;

import entities.*;
import presenters.MessageReplyMenu;
import exceptions.UserFrozenException;
import use_cases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.time.*;
import java.time.format.*;

public class UserMessageReplySystem {
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private TradeManager tradeManager;
    private String accountUsername;
    private MessageReplyMenu messageReplyMenu = new MessageReplyMenu();

    /**
     * Class constructor.
     * Create a new UserMessageReplySystem that controls and allows the user to reply to their messages
     * @param accountUsername the username of the currently logged in User
     * @param userManager the user manager of the system
     * @param tradeManager the trade manager of the system
     * @param globalInventoryManager the global inventory manager of the system
     */
    public UserMessageReplySystem(UserManager userManager, GlobalInventoryManager globalInventoryManager,
                                  TradeManager tradeManager, String accountUsername){
        this.userManager = userManager;
        this.globalInventoryManager = globalInventoryManager;
        this.tradeManager = tradeManager;
        this.accountUsername = accountUsername;
    }

    /**
     * Interacts with the user to allow them to respond and reply to their messages
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Message> messages = new ArrayList<>(userManager.getUserMessages(accountUsername));

        //Initial Menu
        if(messages.size() == 0){
            messageReplyMenu.printNoMessages();
            messageReplyMenu.printExit();
            return;
        }
        try {
            String input = "";
            do {
                messageReplyMenu.printMenuPrompt(messages.size());
                input = br.readLine();
                if(input.equals("2"))return;
                else if(!input.equals("1")) messageReplyMenu.printInvalidInput();
            }while(!input.equals("1"));

            //Going through all the messages the user have
            final List<Message> loopingMessages =  new ArrayList<>(messages);
            for(Message m: loopingMessages){
                if(m instanceof TradeRequest){
                    if(!TradeRequestMessageResponse((TradeRequest) m, messages, br))return;
                }
                else if (m instanceof ContentMessage){
                    if (!ContentMessageResponse((ContentMessage) m, messages, br)) return;
                }
            }
        }catch(IOException e){
            messageReplyMenu.printInvalidInput();
        }finally {
            //Exiting the user from the menu do to an error or that the user has exited
            userManager.setUserMessages(accountUsername, messages);
            messageReplyMenu.printExit();
        }
    }

    //Allow the user to edit a trade request
    private void TradeRequestMessageEdit(TradeRequest m, BufferedReader br){
        String username = m.getSender();
        TradeRequestManager tempTradeRequestManager = new TradeRequestManager(m);

        //warning the user that their trade request is cancelled due to too much edits
        if(!tempTradeRequestManager.canEdit(accountUsername)&&!tempTradeRequestManager.canEdit(username)){
            messageReplyMenu.tradeRequestCancel();
            userManager.createUserMessage(username, "Your trade request:"+m.toString()+
                    "\n is cancelled due to too much edits");
        }
        //Allow the user to edit a trade request
        else{
            String input = "";
            LocalDateTime time = m.getDate();
            String place = m.getPlace();
            try {
                while (!input.equals("1")&&!input.equals("2")&&!input.equals("3")) {
                    messageReplyMenu.printEditTradeRequestPrompt(m);
                    input = br.readLine();
                    boolean valid = false;
                    //Editing the date
                    if(input.equals("2")||input.equals("3")){
                        do{
                            try {
                                messageReplyMenu.changeDatePrompt(time);
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                time = LocalDateTime.parse(br.readLine(), dtf);
                                if(!time.isBefore(LocalDateTime.now()))  valid = true;
                                else{
                                    messageReplyMenu.wrongDate();
                                }
                            } catch (DateTimeParseException e) {
                                messageReplyMenu.wrongFormat();
                            } catch (IOException e) {
                                messageReplyMenu.printErrorOccurred();
                            }
                        }while (!valid);
                    }
                    //Editing the place
                    if(input.equals("1")||input.equals("3")){
                        valid = false;
                        do {
                            try {
                                messageReplyMenu.changePlacePrompt(place);
                                place = br.readLine();
                                valid = true;
                            } catch (IOException e) {
                                messageReplyMenu.printErrorOccurred();
                            }
                        }while (!valid);
                    }
                }
                //Setting the new date/place that the user edit
                tempTradeRequestManager.setDateAndPlace(accountUsername, time, place);
                //Sent the new trade request to other trader TODO
                //userManager.addUserMessage(username, "Your trade request has been edited",
                // tempTradeRequestManager.getTradeRequest(), accountUsername);
                messageReplyMenu.success();
            }
            catch(IOException e){
                messageReplyMenu.printErrorOccurred();
            }
        }
    }
    private boolean TradeRequestMessageResponse(TradeRequest m, ArrayList<Message> messages, BufferedReader br)
            throws IOException{
        messageReplyMenu.printRequestPrompt(m);
        String username = m.getSender();
        TradeRequestManager temp = new TradeRequestManager(m);

        //Warn the user if their trade request cannot be edit anymore
        if(!temp.canEdit(accountUsername)&&!temp.canEdit(username))messageReplyMenu.tradeRequestWarning();
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
                    if(cannotTrade(m.getUserA(),m.getItemA())||cannotTrade(m.getUserB(), m.getItemB())){
                        //Tell the user that their trade cannot be created at this time
                        messageReplyMenu.printCannotTradePrompt();
                        do {
                            input = br.readLine();
                            if(input.equals("2"))done = true;
                            else if(input.equals("1")) {
                                messages.remove(m);
                                //Tell the other trader that the trade could not be created at this time and
                                //the trade request is deleted
                                userManager.createUserMessage(username, "You or the other trader cannot create a " +
                                        "new trade at this time or the items involved or not for trade at this time. " +
                                        "The other trader has chosen to delete this trade request.\n"+
                                        "Trade Request: "+m.toString());
                                done = true;
                            }
                            else messageReplyMenu.printInvalidInput();
                        }while(!done);
                        return true;
                    }
                    //Confirming the trade
                    messages.remove(m);
                    Trade trade = temp.setConfirmation();
                    //Add trade to both user's trade history
                    tradeManager.addTrade(trade);

                    //Removing the items from the GI and personal inventory
                    List<Item> list = new ArrayList<>(trade.getTraderAItemsToTrade());
                    for(Item i:list) {
                        globalInventoryManager.removeItem(i.getItemID());
                    }
                    list = new ArrayList<>(trade.getTraderBItemsToTrade());
                    for(Item i:list) {
                        globalInventoryManager.removeItem(i.getItemID());
                    }
                    messageReplyMenu.success();
                    done = true;
                    break;
                case "2":
                    //Removing and informing the other trade that the request is rejected
                    messages.remove(m);
                    userManager.createUserMessage(username, "Your trade request:"+m.toString()+"\n is rejected by "+
                            accountUsername);
                    messageReplyMenu.success();
                    done = true;
                    break;
                case "3":
                    messages.remove(m);
                    TradeRequestMessageEdit(m, br);
                    done = true;
                    break;
                default:
                    messageReplyMenu.printInvalidInput();
            }
        }while(!done);
        return true;
    }
    private boolean ContentMessageResponse(Message m, List<Message> messages,
                                           BufferedReader br) throws IOException {
        boolean done = false;
        do {
            messageReplyMenu.printContentMessagePrompt(m);
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
                    messageReplyMenu.printInvalidInput();
            }
        }while(!done);
        return true;
    }
    private boolean cannotTrade(String username, List<Item> userItem){
        //Checking if the user can trade
        try{
            if(!userManager.getCanTrade(username, tradeManager.getBorrowedTimes(username),
                    tradeManager.getLendTimes(username),
                    tradeManager.getIncompleteTimes(username),
                    tradeManager.numberOfTradesCreatedThisWeek(username))) return true;
        }catch(UserFrozenException e){
            return true;
        }
        //Checking if the items in the trade is valid aka in the personal inventory and GI
        for(Item i: userItem){
            if(!globalInventoryManager.contains(i))return true;
            boolean contain = false;
            for(Item j: userItem){
                if(i.isEqual(j))contain = true;
            }
            if(!contain)return true;
        }
        return false;
    }
}
