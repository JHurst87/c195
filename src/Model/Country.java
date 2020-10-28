package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Country {
    private int countryId;
    private String country;

    public Country(int countryId, String country){
        this.countryId = countryId;
        this.country = country;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString(){
        return this.getCountry();
    }
}
