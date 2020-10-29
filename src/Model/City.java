package Model;

public class City {
    private int cityId;
    private String cityName;
    private Country country;

    public City(){

    }

    public City(int cityId, String cityName, Country country){
        this.cityId = cityId;
        this.cityName = cityName;
        this.country = country;
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
