package src.backend.model;

public class EndangeredAnimal {
    private String ScientificName;
    private String CommonName;
    private String Type;
    private String Habitat;
    private String Appearance;

    public EndangeredAnimal(String ScientificName, String CommonName, String Type, String Habitat, String Appearance) {
        this.ScientificName = ScientificName;
        this.CommonName = CommonName;
        this.Type = Type;
        this.Habitat = Habitat;
        this.Appearance = Appearance;
    }

    public String getScientificName() {
        return ScientificName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
    }

    public String getCommonName() {
        return CommonName;
    }

    public void setCommonName(String commonName) {
        CommonName = commonName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getHabitat() {
        return Habitat;
    }

    public void setHabitat(String habitat) {
        Habitat = habitat;
    }

    public String getAppearance() {
        return Appearance;
    }

    public void setAppearance(String appearance) {
        Appearance = appearance;
    }
}
