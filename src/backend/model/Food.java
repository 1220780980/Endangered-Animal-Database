package src.backend.model;

public class Food {
    private String DescriptionOfFoodSource;
    private String Seasonality;
    private String Name;

    public Food(String DescriptionOfFoodSource, String Seasonality, String Name) {
        this.DescriptionOfFoodSource = DescriptionOfFoodSource;
        this.Seasonality = Seasonality;
        this.Name = Name;
    }

    public String getDescriptionOfFoodSource() {
        return DescriptionOfFoodSource;
    }

    public void setDescriptionOfFoodSource(String descriptionOfFoodSource) {
        DescriptionOfFoodSource = descriptionOfFoodSource;
    }

    public String getSeasonality() {
        return Seasonality;
    }

    public void setSeasonality(String seasonality) {
        Seasonality = seasonality;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
