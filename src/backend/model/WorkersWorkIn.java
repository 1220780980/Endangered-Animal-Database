package src.backend.model;

public class WorkersWorkIn {
    private int ID;
    private String Name;
    private String Gender;
    private String Address;

    public WorkersWorkIn(int ID, String Name, String Gender, String Address) {
        this.ID = ID;
        this.Name = Name;
        this.Gender = Gender;
        this.Address = Address;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}