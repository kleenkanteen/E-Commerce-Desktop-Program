public class Item {
    String name;
    boolean wantToTrade = false;
    String ownerName;
    String description = null;
    boolean permanent = true; // Permanent - true, temporary - False, default is true
    boolean approval = false;


    Item(String name, String ownerName){
        this.name = name;
        this.ownerName = ownerName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public boolean isEqual(Item banana){
        return banana.description.equals(description) && banana.name.equals(name) && banana.ownerName.equals(ownerName);
    }
}



