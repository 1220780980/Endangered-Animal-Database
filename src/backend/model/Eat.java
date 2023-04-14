package src.backend.model;

public class Eat {
    private String FoodName;
    private String ScientificName;

    public Eat(String FoodName, String ScientificName) {
        this.FoodName = FoodName;
        this.ScientificName = ScientificName;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getScientificName() {
        return ScientificName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
    }
}
