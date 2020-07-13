package F_entities;

import java.io.Serializable;

public abstract class DecisionMessage extends Message implements Serializable {
    private String[] options;

    /**
     * Class contructor.
     * A message sent by the system with a content and some decisions a person can make based on the content
     * @param content is the content of the message
     * @param options the options that can be made
     */
    DecisionMessage(String content, String[] options){
        super(content);
        this.options = options;
    }

    /**
     * Class contructor.
     * A message sent by an account with a content and some decisions a person can make based on the content
     * @param content is the content of the message
     * @param options the options that can be made
     * @param username is the sender's username
     */
    DecisionMessage(String content, String[] options, String username){
        super(content, username);
        this.options = options;
    }

    /**
     * Getter for the options of this decision message
     * @return the options of this decision message
     */
    public String[] getOptions(){
        return options;
    }

    /**
     * Returns a string representation of the message
     * @return the content and decisions of the message in a string representation
     */
    @Override
    public String toString() {
        String optionsToString = "Choose your option below:\n";
        for(int i=0; i<options.length; i++){
            if(i != 0) optionsToString = optionsToString+"\n";
            optionsToString = optionsToString+"["+Integer.toString(i+1)+"] "+options[i];
        }
        return optionsToString;
    }
}
