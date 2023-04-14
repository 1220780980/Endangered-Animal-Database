package src.backend.model;

import java.sql.Date;

public class Threat {
    private int ThreatID;
    private String PlaceIdentified;
    private String DescriptionOfThreat;
    private int ThreatSeverity;
    private java.sql.Date Date;

    public Threat(int ThreatID, String PlaceIdentified, String DescriptionOfThreat, int ThreatSeverity, Date Date) {
        this.ThreatID = ThreatID;
        this.PlaceIdentified = PlaceIdentified;
        this.DescriptionOfThreat = DescriptionOfThreat;
        this.ThreatSeverity = ThreatSeverity;
        this.Date = Date;
    }

    public int getThreatID() {
        return ThreatID;
    }

    public void setThreatID(int threatID) {
        ThreatID = threatID;
    }

    public String getPlaceIdentified() {
        return PlaceIdentified;
    }

    public void setPlaceIdentified(String placeIdentified) {
        PlaceIdentified = placeIdentified;
    }

    public String getDescriptionOfThreat() {
        return DescriptionOfThreat;
    }

    public void setDescriptionOfThreat(String descriptionOfThreat) {
        DescriptionOfThreat = descriptionOfThreat;
    }

    public int getThreatSeverity() {
        return ThreatSeverity;
    }

    public void setThreatSeverity(int threatSeverity) {
        ThreatSeverity = threatSeverity;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }
}
