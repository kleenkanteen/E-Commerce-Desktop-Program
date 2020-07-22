package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import entities.UnfreezeRequestMessage;
import presenters.MessageReplyMenu;
import use_cases.UserManager;

public class AdminMessageReplySystem {
    private use_cases.AdminManager am;
    private use_cases.GlobalInventoryManager gi;
    private use_cases.UserManager um;
    private String accountUsername;
    private MessageReplyMenu mm;

    /**
     * Class constructor.
     * Create a new UserMessageReplySystem that controls and allows the admin to reply to system messages
     * @param accountUsername the username of the currently logged in User
     * @param um the user manager of the system
     * @param am the admin manager of the system
     * @param gi the global inventory manager of the system
     */
    public AdminMessageReplySystem(use_cases.AdminManager am, use_cases.GlobalInventoryManager gi, UserManager um, String accountUsername){
        this.am = am;
        this.gi = gi;
        this.um = um;
        this.accountUsername = accountUsername;
        mm = new MessageReplyMenu();
    }

    /**
     * Interacts with the admin to allow them to respond and reply to system messages
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<entities.Message> messages = am.getAdminMessagesArrayList();
        //Initial Menu
        if(messages.size() == 0){
            mm.printNoMessages();
            mm.printExit();
            return;
        }
        try {
            String input = "";
            do{
                mm.printMenuPrompt(messages.size());
                input = br.readLine();
                if(input.equals("2"))return;
                else if(!input.equals("1")) mm.printInvalidInput();
            }while(!input.equals("1"));

            //Going through all the messages the user have
 ;          final ArrayList<entities.Message> loopingMessages =  new ArrayList<entities.Message>(messages);
            for(entities.Message m: loopingMessages){
                if(m instanceof entities.NewItemMessage){
                    if(!NewItemMessageResponse((entities.NewItemMessage) m, messages, br))return;
                }
                else if(m instanceof entities.FreezeRequestMessage){
                    if(!FreezeRequestMessageResponse((entities.FreezeRequestMessage) m, messages, br))return;
                }
                else if(m instanceof entities.UnfreezeRequestMessage){
                    if(!UnfreezeRequestMessageResponse((entities.UnfreezeRequestMessage) m, messages, br))return;
                }
                else {
                    if (!ContentMessageResponse(m, messages, br)) return;
                }
            }
        }catch(IOException e){
            mm.printErrorOccurred();
        }finally {
            am.setAdminMessagesArrayList(messages);
            mm.printExit();
        }
    }
    private boolean ContentMessageResponse(entities.Message m, ArrayList<entities.Message> messages,
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
    private boolean UnfreezeRequestMessageResponse(UnfreezeRequestMessage m, ArrayList<entities.Message> messages,
                                                   BufferedReader br) throws IOException{
        String u = m.getUser();
        boolean done = false;
        do{
            mm.printDecisionMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "a":
                    done = true;
                    break;
                case "b":
                    return false;
                case "1":
                    //Unfreezing the user aka accepting the unfreeze request
                    um.freezeUserAccount(u, false);
                    messages.remove(m);
                    //Informing the other user
                    um.createUserMessage(u, "Your account is unfrozen by the Admin "+accountUsername);
                    mm.success();
                    done = true;
                    break;
                case "2":
                    //Ignoring the message
                    messages.remove(m);
                    //Informing the other user
                    um.createUserMessage(u, "Your request is rejected by the Admin "+accountUsername);
                    mm.success();
                    done = true;
                    break;
                 default:
                     mm.printInvalidInput();
            }
        }while(!done);
        return true;
    }
    private boolean FreezeRequestMessageResponse(entities.FreezeRequestMessage m, ArrayList<entities.Message> messages,
                                                 BufferedReader br) throws IOException{
        String u = m.getUser();
        boolean done = false;
        do {
            mm.printDecisionMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "a":
                    done = true;
                    break;
                case "b":
                    return false;
                case "1":
                    //freezing the user
                    um.freezeUserAccount(u, true);
                    messages.remove(m);
                    //informing the other user
                    um.createUserMessage(u, "Your account is frozen by the Admin "+accountUsername);
                    mm.success();
                    done = true;
                    break;
                case "2":
                    //Ignoring the request
                    messages.remove(m);
                    mm.success();
                    done = true;
                    break;
                default:
                    mm.printInvalidInput();
            }
        }while(!done);
        return true;
    }

    private boolean NewItemMessageResponse(entities.NewItemMessage m, ArrayList<entities.Message> messages,
                                           BufferedReader br) throws IOException{
        entities.Item item = m.getNewItem();
        boolean done = false;
        do {
            mm.printDecisionMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "a":
                    done = true;
                    break;
                case "b":
                    return false;
                case "1":
                    //Accepting the new item

                    //Adding the new message to the GI
                    gi.addItemToHashMap(item);
                    //Adding the new message to the personal inventory
                    um.addItemToUserInventory(item, item.getOwnerName());
                    messages.remove(m);
                    //Informing the other user
                    um.createUserMessage(item.getOwnerName(), "Your Item: "+item+
                            "\n has been successfully added to the system by the Admin "+accountUsername);
                    mm.success();
                    done = true;
                    break;
                case "2":
                    //Denying the new item
                    messages.remove(m);
                    //Informing the other user
                    um.createUserMessage(item.getOwnerName(), "Your Item: "+item+
                            "\n has been rejected by the Admin "+accountUsername);
                    mm.success();
                    done = true;
                    break;
                default:
                    mm.printInvalidInput();
            }
        }while(!done);
        return true;
    }
}