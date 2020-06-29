package entities;

public class Item {
    private String ID = null;
    private final String description;
    private final String name;
    private String ownerName; // The UserName of owner

    Item(String name, String ownerName, String description, boolean permanent){
        this.name = name;
        this.ownerName = ownerName;
        this.description = description;

    }

    @Override
    public String toString() {
        return "entities.Item{" +
                "ID='" + ID + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setID(String i){
        ID = i ;
    }

    public String getID(){
        return ID;
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
        return item.getDescription().equals(description) &&
                item.getName().equals(name) && item.getOwnerName().equals(ownerName);
    }

}



