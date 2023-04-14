package src.backend.model;

public class Continents {
    private String Name;
    private int NumberOfCountries;

    public Continents(String Name, int NumberOfCountries) {
        this.Name = Name;
        this.NumberOfCountries = NumberOfCountries;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNumberOfCountries() {
        return NumberOfCountries;
    }

    public void setNumberOfCountries(int numberOfCountries) {
        NumberOfCountries = numberOfCountries;
    }
}
