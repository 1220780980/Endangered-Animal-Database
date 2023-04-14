package src.backend.model;

public class CountriesPartOf {
    private String CountryName;
    private String Capital;
    private int Population;
    private String OfficialLanguage;
    private String Continent;

    public CountriesPartOf(String CountryName, String Capital, int Population, String OfficialLanguage, String Continent) {
        this.CountryName = CountryName;
        this.Capital = Capital;
        this.Population = Population;
        this.OfficialLanguage = OfficialLanguage;
        this.Continent = Continent;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public int getPopulation() {
        return Population;
    }

    public void setPopulation(int population) {
        Population = population;
    }

    public String getOfficialLanguage() {
        return OfficialLanguage;
    }

    public void setOfficialLanguage(String officialLanguage) {
        OfficialLanguage = officialLanguage;
    }

    public String getContinent() {
        return Continent;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }
}
