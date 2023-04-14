package src.backend.model;

public class NaturalDisaster {
    private int ThreatID;
    private String Type;

    public NaturalDisaster(int ThreatID, String Type) {
        this.ThreatID = ThreatID;
        this.Type = Type;
    }

    public int getThreatID() {
        return ThreatID;
    }

    public void setThreatID(int threatID) {
        ThreatID = threatID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
