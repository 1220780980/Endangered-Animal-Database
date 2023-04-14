package src.backend.model;

public class Live {
    private String ScientificName;
    private String EnvironmentName;
    private String Biome;

    public Live(String ScientificName, String EnvironmentName, String Biome) {
        this.ScientificName = ScientificName;
        this.EnvironmentName = EnvironmentName;
        this.Biome = Biome;
    }

    public String getScientificName() {
        return ScientificName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
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
