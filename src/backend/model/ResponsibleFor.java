package src.backend.model;

public class ResponsibleFor {
    private String CountryName;
    private String OrganizationAddress;

    public ResponsibleFor(String CountryName, String OrganizationAddress) {
        this.CountryName = CountryName;
        this.OrganizationAddress = OrganizationAddress;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getOrganizationAddress() {
        return OrganizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        OrganizationAddress = organizationAddress;
    }
}
