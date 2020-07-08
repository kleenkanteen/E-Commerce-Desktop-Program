package entities;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Set;
import java.io.Serializable;

public class User extends Account implements Serializable{
    private ArrayList<Item> personalInventory = new ArrayList<>();
    private ArrayList<Item> wishlist = new ArrayList<>();
    private ArrayList<Trade> tradeHistory = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private boolean frozenStatus = true;
    private int tradePerWeek = 5;
    private int theshold = 1;
    private int limitOfImcompleteTrade = 5;


    /**
     * Creates an User with the given username and password.
     * This User has the default values:
     * - Limit of 5 trades per week
     * - Must have 1 more lent than borrow in their trade history
     * - Limit of 5 imcomplete trades at once
     * @param username is the username of this User
     * @param password is the password of this User
     */
    public User(String username, String password) {
        super(username, password);
    }

    /**
     * Getter of the personal inventory of this account
     * @return the personal inventory of this account
     */
    public ArrayList<Item> getPersonalInventory(){
        return personalInventory;
    }

    /**
     * Getter of the wishlist of this account
     * @return the wishlist of this account
     */
    public ArrayList<Item> getWishlist(){
        return wishlist;
    }

    /**
     * Getter of the trade history of this account
     * @return the trade history of this account
     */
    public ArrayList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    /**
     * Getter of all the temporary trade history of this account
     * @return all the temporary trade history of this account
     */
    public ArrayList<TempTrade> getTempTradeHistory() {
        ArrayList <TempTrade> tempTradeHistory = new ArrayList<TempTrade>();
        for (Trade t: tradeHistory){
            if(t instanceof TempTrade) tempTradeHistory.add((TempTrade) t);
        }
        return tempTradeHistory;
    }

    /**
     * Getter of all the incomplete trade history of this account
     * @return all the incomplete trade history of this account
     */
    public ArrayList<Trade> getIncompleteTradeHistory() {
        ArrayList <Trade> incompleteTradeHistory = new ArrayList<Trade>();
        for (Trade t: tradeHistory){
            if(!t.getCompleted())incompleteTradeHistory.add(t);
        }
        return incompleteTradeHistory;
    }

    /**
     * Getter of the number of times this user has borrowed
     * @return the number of times this user has borrowed
     */
    public int getBorrowedTimes() {
        int total = 0;
        for(Trade t: tradeHistory){
            if(t.isBorrowed(getUsername()))total++;
        }
        return total;
    }

    /**
     * Getter of the number of times this user has lend
     * @return the number of times this user has lend
     */
    public int getLendTimes() {
        int total = 0;
        for(Trade t: tradeHistory){
            if(t.isLent(getUsername()))total++;
        }
        return total;
    }

    /**
     * Getter of the 3 most frequent trading partners of this user's username
     * @return the 3 most frequent trading partners username
     */
    public String[] getFrequentTradingPartners() {
        TreeMap<Integer, ArrayList<String>> counter = new TreeMap<Integer, ArrayList<String>>();
        ArrayList<String> partners = new ArrayList<String>();
        String[] tradingPartners = new String[3];
        for(Trade t: tradeHistory){
            String partner = t.tradingPartner(getUsername());
            if(partner == null)continue;
            partners.add(partner);
        }
        for(String u: partners) {
            int n = count(partners, u);
            if (counter.containsKey(n)) {
                ArrayList<String> list = counter.get(n);
                if (!list.contains(u)) list.add(u);
            } else {
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(u);
                counter.put(n, temp);
            }
        }
        Set<Integer> keys = counter.descendingKeySet();
        for(Integer key: keys){
           ArrayList<String> p = counter.get(key);
           for(int i = 0; i< p.size(); i++){
               for(int j=0; j<3; j++){
                   if(tradingPartners[j] == (null)){
                       tradingPartners[j] = p.get(i);
                       break;
                   }
                   if(tradingPartners[j].equals(p.get(i)))break;
               }
               if(tradingPartners[2] != (null))break;
           }
           if(tradingPartners[2]!=(null))break;
        }

        return tradingPartners;

    }
    private int count(ArrayList<String> list, String item){
        int sum = 0;
        for(String u: list){
            if(u.equals(item))sum++;
        }
        return sum;
    }
    /**
     * Getter of the theshold (how many more times must you lend items before you can borrow) value of this user
     * @return the theshold value
     */
    public int getTheshold() {
        return theshold;
    }

    /**
     * Getter of the limit of imcomplete trades this user can have
     * @return the value of the limit of imcomplete trades
     */
    public int getLimitOfImcompleteTrade() {
        return limitOfImcompleteTrade;
    }

    /**
     * Getter of the limited of times the user can trade in a week of this account
     * @return the limited of times the user can trade of this account
     */
    public int getTradePerWeek(){
        return tradePerWeek;
    }

    /**
     * Getter of the frozen status of this account
     * @return the frozen status of this account
     */
    public boolean getFrozenStatus(){
        return frozenStatus;
    }

    /**
     * Getter of the messages of this account
     * @return the messages of this account
     */
    public ArrayList<Message> getMessages(){
        return messages;
    }

    /**
     * Setting a new personal inventory to this account
     * @param personalInventory the new personal inventory
     */
    public void setPersonalInventory(ArrayList<Item> personalInventory){
        this.personalInventory = personalInventory;
    }

    /**
     * Setting a new wishlist to this account
     * @param wishlist the new wishlist
     */
    public void setWishlist(ArrayList<Item> wishlist){
        this.wishlist = wishlist;
    }

    /**
     * Adding the most recent trade of this entities.User to the entities.User's trade history
     * @param trade the most recent trade of this entities.User
     */
    public void addTradeHistory(Trade trade){
        tradeHistory.add(trade);
    }

    /**
     * Setting the frozen status to this account
     * @param status the new frozen status to this account
     */
    public void setFrozenStatus(boolean status){
        this.frozenStatus = status;
    }


    /**
     * Setting a new list of messages to this account
     * @param messages the new messages of this account
     */
    public void setMessages(ArrayList<Message> messages){
        this.messages = messages;
    }

    /**
     * Changing the theshold (how many more times must you lend items before you can borrow) that this user has
     * @param newTheshold the new theshold value
     */
    public void setTheshold(int newTheshold){
        theshold = newTheshold;
    }

    /**
     * Changing the limit of imcomplete trades this user can have
     * @param newLimit the new limit
     */
    public void setLimitOfImcompleteTrade(int newLimit){
        limitOfImcompleteTrade = newLimit;
    }

    /**
     * Changing the limited of times that this user can trade in a week
     * @param tradePerWeek the new trade per week value
     */
    public void setTradePerWeek(int tradePerWeek){
        this.tradePerWeek = tradePerWeek;
    }
}

