package frontend.MessageReplySystem;

import entities.Message;
import presenters.MessageReplyPresenter;

public class SystemMessageResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private Message message;

    SystemMessageResponse(Message message){
        this.message = message;
    }
    @Override
    public String[] getActions() {
        return messageReplyPresenter.systemMessageActionPrompt(message);
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            System.out.println("Delete");
        }
    }
}
