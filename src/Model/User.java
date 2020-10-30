package Model;

public class User {
    String name;
    int userId;

    public User(){

    }

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
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

    public String toString(){
        return this.name;
    }
}
