package src.backend.model;

public class Harm {
    private int ThreatID;
    private String ScientificName;

    public Harm(int ThreatID, String ScientificName) {
        this.ThreatID = ThreatID;
        this.ScientificName = ScientificName;
    }

    public int getThreatID() {
        return ThreatID;
    }

    public void setThreatID(int threatID) {
        ThreatID = threatID;
    }

    public String getScientificName() {
        return ScientificName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
    }
}
