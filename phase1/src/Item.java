public class Item {
    private final String description;
    private final String name;
    String ownerName; // The UserName of owner

    Item(String name, String ownerName, String description, boolean permanent){
        this.name = name;
        this.ownerName = ownerName;
        this.description = description;

    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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



