package Model;

import utils.DBQuery;

import java.util.Calendar;

public class User {
    String name;
    int userId;
    String password;
    Boolean active;
    Calendar createDate;
    String createdBy;
    Calendar lastUpdate;
    String lastUpdatedBy;

    public User(){

    }

    public User(int userId, String name, String password, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.active = active;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;


    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int id){
        this.userId = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}
