package src.backend.model;

public class Help {
    private int WorkerID;
    private String Address;
    private String ScientificName;
    private String Services;

    public Help(int WorkerID, String Address, String ScientificName, String Services) {
        this.WorkerID = WorkerID;
        this.Address = Address;
        this.ScientificName = ScientificName;
        this.Services = Services;
    }

    public int getWorkerID() {
        return WorkerID;
    }

    public void setWorkerID(int workerID) {
        WorkerID = workerID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getScientificName() {
        return ScientificName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
    }

    public String getServices() {
        return Services;
    }

    public void setServices(String services) {
        Services = services;
    }
}
