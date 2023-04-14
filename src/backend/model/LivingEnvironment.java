package src.backend.model;

public class LivingEnvironment {
    private String Name;
    private String Biome;

    public LivingEnvironment(String Name, String Biome) {
        this.Name = Name;
        this.Biome = Biome;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBiome() {
        return Biome;
    }

    public void setBiome(String biome) {
        Biome = biome;
    }
}