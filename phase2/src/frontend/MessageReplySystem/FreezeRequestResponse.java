package frontend.MessageReplySystem;

import presenters.MessageReplyPresenter;
import entities.Message;
import entities.Request;

public class FreezeRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private Message message;

    FreezeRequestResponse(Message message){
        this.message = message;
    }
    @Override
    public String[] getActions() {
        return messageReplyPresenter.requestActionPrompt((Request) message);
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            System.out.println("Freeze");
        }
        else if(action.equals(validActions[1])){
            System.out.println("Delete");
        }
    }
}
