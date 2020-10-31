package Model;

import Exception.CityException;

public class City {
    private int cityId;
    private String cityName;
    private Country country;

    public City(){
        this.cityId = 0;
        this.cityName = "";
        this.country = null;
    }

    public City(int cityId, String cityName, Country country){
        this.cityId = cityId;
        this.cityName = cityName;
        this.country = country;
    }

    public boolean isValid() throws CityException {
        if(this.cityName.equals("")){
            throw new CityException("City name is required");
        }

        if(this.country == null){
            throw new CityException("Country is required");
        }
        return true;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry(){
        return this.country;
    }

    public void setCountry(Country country){
        this.country = country;
    }

    public String toString(){
        return this.cityName;
    }
}
