package frontend.MessageReplySystem;

import entities.UnfreezeRequest;
import presenters.MessageReplyPresenter;
import entities.Message;
import entities.Request;
import use_cases.MessageBuilder;
import use_cases.UserManager;

import java.util.List;

public class UnfreezeRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private UnfreezeRequest message;
    private List<Message> messageList;
    private UserManager userManager;

    UnfreezeRequestResponse(UnfreezeRequest message, List<Message> messageList, UserManager userManager){
        this.message = message;
        this.messageList = messageList;
        this.userManager = userManager;
    }
    @Override
    public String[] getActions() {
        return messageReplyPresenter.requestAction(message);
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            messageList.remove(message);

            String username = message.getUser();
            MessageBuilder messageBuilder = new MessageBuilder();
            userManager.freezeUserAccount(username);
            //informing the other user
            userManager.addUserMessage(username,
                    messageBuilder.getSystemMessage("Your account is unfrozen"));
        }
        else if(action.equals(validActions[1])){
            messageList.remove(message);

            String username = message.getUser();
            MessageBuilder messageBuilder = new MessageBuilder();
            //Informing the other user
            userManager.addUserMessage(username,
                    messageBuilder.getSystemMessage("Your request is rejected"));

        }
    }
}
