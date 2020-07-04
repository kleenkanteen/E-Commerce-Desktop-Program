package controller_presenter_gateway;

import entities.Message;

import java.util.ArrayList;

public class MessageReplyMenu {
    private ArrayList<Message> messages;
    public MessageReplyMenu(ArrayList<Message> messages){
        this.messages = messages;

    }
    public void printMenu(){
        System.out.println("You have "+messages.size()+" messages");
        System.out.println("Type 'exit' to quit or 'view' to look through the messages.");
    }
    public void printMessage(int n){
        System.out.println("Message "+Integer.toString(n+1)+": "+messages.get(n));
    }
    public void printContentMessagePrompt(){
        System.out.println("Enter 'delete' to delete this message or 'next' to skip this " +
                "message and view the next message or 'exit' to exit");
    }
    public void printDecisionMessagePrompt(){
        System.out.println("Enter an option number or 'next' to skip this message " +
                "and view the next message or 'exit' to exit");
    }

}
