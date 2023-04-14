package src.backend.model;

public class Contains {
    private String CountriesName;
    private String EnvironmentName;
    private String Biome;

    public Contains(String CountriesName, String EnvironmentName, String Biome) {
        this.CountriesName = CountriesName;
        this.EnvironmentName = EnvironmentName;
        this.Biome = Biome;
    }

    public String getCountriesName() {
        return CountriesName;
    }

    public void setCountriesName(String countriesName) {
        CountriesName = countriesName;
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
}
