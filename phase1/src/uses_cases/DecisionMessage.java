package uses_cases;

import entities.Message;

public abstract class DecisionMessage extends Message {
    private String[] options;

    /**
     * A message sent to a entities.User with a content and a decision that can be made
     * @param content is the content of the message
     * @param options the options that can be made in the decision
     */
    protected DecisionMessage(String content, String[] options){
        super(content);
        this.options = options;
    }

    /**
     * Getter for the options of this decision message
     * @return the options of this decision message
     */
    protected String[] getOptions(){
        return options;
    }

    /**
     * Returns a string representation of the message
     * @return the content and decisions of the message in a string representation
     */
    @Override
    public String toString() {
        String optionsToString = "";
        for(int i=0; i<options.length; i++){
            if(i != 0) optionsToString = optionsToString+"\n";
            optionsToString = optionsToString+Integer.toString(i+1)+": "+options[i];
        }
        return super.toString() + "\n" + optionsToString;
    }
}
