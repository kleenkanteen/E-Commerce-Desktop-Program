package frontend.MessageReplySystem;

import entities.Message;
import presenters.MessageReplyPresenter;

import java.util.List;

public class SystemMessageResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private Message message;
    private List<Message> messageList;

    SystemMessageResponse(Message message, List<Message> messageList){
        this.message = message;
        this.messageList = messageList;
    }
    @Override
    public String[] getActions() {
        return messageReplyPresenter.systemMessageAction();
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            messageList.remove(message);
        }
    }
}
