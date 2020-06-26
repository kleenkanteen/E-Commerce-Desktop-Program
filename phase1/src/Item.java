public class Item {
    String name;
    boolean wantToTrade = false; // set to true if the User wants others to see that item
    String ownerName; // The UserName of owner, "and change when trade is made?"
    // Question, should we track the ownerName?
    String description = null; //This can be modified later by the owner
    boolean permanent = true; // Permanent - true, temporary - False, default value is true
    boolean approval = false;


    Item(String name, String ownerName, String description){
        this.name = name;
        this.ownerName = ownerName;
        this.description = description;

    }

    public void setName(String name) {
        this.name = name;
    }


    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public void setWantToTrade(boolean wantToTrade) {
        this.wantToTrade = wantToTrade;
    }



    public boolean getApproval() {
        return approval;
    }

    public boolean getPermanent() {
        return permanent;
    }

    public boolean getWantToTrade() {
        return wantToTrade;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }
    public boolean isEqual(Item item){
        return item.description.equals(description) && item.name.equals(name) && item.ownerName.equals(ownerName);
    }

}



