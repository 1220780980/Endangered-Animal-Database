package src.backend.model;

import java.sql.Date;

public class EndangeredIn {
    private String ScientificName;
    private String Countries;
    private String Status;
    private int Population;
    private java.sql.Date Date;

    public EndangeredIn(String ScientificName, String Countries, String Status, int Population, Date Date) {
        this.ScientificName = ScientificName;
        this.Countries = Countries;
        this.Status = Status;
        this.Population = Population;
        this.Date = Date;
    }

    public String getScientificName() {
        return ScientificName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
    }

    public String getCountries() {
        return Countries;
    }

    public void setCountries(String countries) {
        Countries = countries;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getPopulation() {
        return Population;
    }

    public void setPopulation(int population) {
        Population = population;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }
}
