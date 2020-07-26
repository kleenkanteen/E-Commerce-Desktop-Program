package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import entities.*;
import presenters.MessageReplyMenu;
import use_cases.*;

public class AdminMessageReplySystem {
    private AdminManager adminManager;
    private GlobalInventoryManager globalInventoryManager;
    private UserManager userManager;
    private String accountUsername;
    private MessageReplyMenu messageReplyMenu = new MessageReplyMenu();
    private MessageBuilder messageBuilder = new MessageBuilder();

    /**
     * Class constructor.
     * Create a new UserMessageReplySystem that controls and allows the admin to reply to system messages
     * @param accountUsername the username of the currently logged in User
     * @param userManager the user manager of the system
     * @param adminManager the admin manager of the system
     * @param globalInventoryManager the global inventory manager of the system
     */
    public AdminMessageReplySystem(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                                   UserManager userManager, String accountUsername){
        this.adminManager = adminManager;
        this.globalInventoryManager = globalInventoryManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;
    }

    /**
     * Interacts with the admin to allow them to respond and reply to system messages
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Message> messages = new ArrayList<>(adminManager.getAdminMessages());
        //Initial Menu
        if(messages.size() == 0){
            messageReplyMenu.printNoMessages();
            messageReplyMenu.printExit();
            return;
        }
        try {
            String input = "";
            do{
                messageReplyMenu.printMenuPrompt(messages.size());
                input = br.readLine();
                if(input.equals("2"))return;
                else if(!input.equals("1")) messageReplyMenu.printInvalidInput();
            }while(!input.equals("1"));

            //Going through all the messages the user have
 ;          final List<Message> loopingMessages =  new ArrayList<>(messages);
            for(Message m: loopingMessages){
                if(m instanceof NewItemRequest){
                    if(!NewItemMessageResponse((NewItemRequest) m, messages, br))return;
                }
                else if(m instanceof FreezeRequest){
                    if(!FreezeRequestMessageResponse((FreezeRequest) m, messages, br))return;
                }
                else if(m instanceof UnfreezeRequest){
                    if(!UnfreezeRequestMessageResponse((UnfreezeRequest) m, messages, br))return;
                }
                else if(m instanceof ContentMessage){
                    if (!ContentMessageResponse((ContentMessage) m, messages, br)) return;
                }
            }
        }catch(IOException e){
            messageReplyMenu.printErrorOccurred();
        }finally {
            adminManager.setAdminMessages(messages);
            messageReplyMenu.printExit();
        }
    }
    private boolean ContentMessageResponse(ContentMessage m, List<Message> messages,
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
    private boolean UnfreezeRequestMessageResponse(UnfreezeRequest m, List<Message> messages,
                                                   BufferedReader br) throws IOException{
        String u = m.getUser();
        boolean done = false;
        do{
            messageReplyMenu.printRequestPrompt(m);
            String input = br.readLine();
            switch (input){
                case "a":
                    done = true;
                    break;
                case "b":
                    return false;
                case "1":
                    //Unfreezing the user aka accepting the unfreeze request
                    userManager.unFreezeUserAccount(u);
                    messages.remove(m);
                    //Informing the other user
                    userManager.addUserMessage(u,
                            messageBuilder.getContentMessage("Your account is unfrozen by the Admin "+accountUsername));
                    messageReplyMenu.success();
                    done = true;
                    break;
                case "2":
                    //Ignoring the message
                    messages.remove(m);
                    //Informing the other user
                    userManager.addUserMessage(u,
                            messageBuilder.getContentMessage("Your request is rejected by the Admin "+accountUsername));
                    messageReplyMenu.success();
                    done = true;
                    break;
                 default:
                     messageReplyMenu.printInvalidInput();
            }
        }while(!done);
        return true;
    }
    private boolean FreezeRequestMessageResponse(FreezeRequest m, List<Message> messages,
                                                 BufferedReader br) throws IOException{
        String u = m.getUser();
        boolean done = false;
        do {
            messageReplyMenu.printRequestPrompt(m);
            String input = br.readLine();
            switch (input){
                case "a":
                    done = true;
                    break;
                case "b":
                    return false;
                case "1":
                    //freezing the user
                    userManager.freezeUserAccount(u);
                    messages.remove(m);
                    //informing the other user
                    userManager.addUserMessage(u,
                            messageBuilder.getContentMessage("Your account is frozen by the Admin "+accountUsername));
                    messageReplyMenu.success();
                    done = true;
                    break;
                case "2":
                    //Ignoring the request
                    messages.remove(m);
                    messageReplyMenu.success();
                    done = true;
                    break;
                default:
                    messageReplyMenu.printInvalidInput();
            }
        }while(!done);
        return true;
    }

    private boolean NewItemMessageResponse(NewItemRequest m, List<Message> messages,
                                           BufferedReader br) throws IOException{
        Item item = m.getNewItem();
        boolean done = false;
        do {
            messageReplyMenu.printRequestPrompt(m);
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
                    globalInventoryManager.addItemToHashMap(item);
                    messages.remove(m);
                    //Informing the other user
                    userManager.addUserMessage(item.getOwnerName(),
                            messageBuilder.getContentMessage("Your Item: "+item+
                                    "\n has been successfully added to the system by the Admin "+accountUsername));
                    messageReplyMenu.success();
                    done = true;
                    break;
                case "2":
                    //Denying the new item
                    messages.remove(m);
                    //Informing the other user
                    userManager.addUserMessage(item.getOwnerName(),
                            messageBuilder.getContentMessage("Your Item: "+item+
                                    "\n has been rejected by the Admin "+accountUsername));
                    messageReplyMenu.success();
                    done = true;
                    break;
                default:
                    messageReplyMenu.printInvalidInput();
            }
        }while(!done);
        return true;
    }
}
