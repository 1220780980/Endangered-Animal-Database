package src.backend.model;

public class Organizations {
    private String Address;
    private String Name;
    private String PhoneNumber;
    private String FundedBy;

    public Organizations(String Address, String Name, String PhoneNumber, String FundedBy) {
        this.Address = Address;
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.FundedBy = FundedBy;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFundedBy() {
        return FundedBy;
    }

    public void setFundedBy(String fundedBy) {
        FundedBy = fundedBy;
    }
}
