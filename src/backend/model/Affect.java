package src.backend.model;

import java.sql.Date;

public class Affect {
    private int ThreatID;
    private String EnvironmentName;
    private String Biome;
    private Date DateLastAffected;

    public Affect(int ThreatID, String EnvironmentName, String Biome, Date DateLastAffected) {
        this.ThreatID = ThreatID;
        this.EnvironmentName = EnvironmentName;
        this.Biome = Biome;
        this.DateLastAffected = DateLastAffected;
    }

    public int getThreatID() {
        return ThreatID;
    }

    public void setThreatID(int threatID) {
        ThreatID = threatID;
    }

    public String getEnvironmentName() {
        return EnvironmentName;
    }

    public void setEnvironmentName(String environmentName) {
        EnvironmentName = environmentName;
    }

    public String getBiome() {
        return Biome;
    }

    public void setBiome(String biome) {
        Biome = biome;
    }

    public Date getDateLastAffected() {
        return DateLastAffected;
    }

    public void setDateLastAffected(Date dateLastAffected) {
        DateLastAffected = dateLastAffected;
    }
}
