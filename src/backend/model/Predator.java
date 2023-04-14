package src.backend.model;

public class Predator {
    private int ThreatID;
    private String Name;

    public Predator(int ThreatID, String Name) {
        this.ThreatID = ThreatID;
        this.Name = Name;
    }

    public int getThreatID() {
        return ThreatID;
    }

    public void setThreatID(int threatID) {
        ThreatID = threatID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
