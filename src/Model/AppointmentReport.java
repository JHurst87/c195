package Model;

public class AppointmentReport {
    private String month;
    private String count;
    private String type;

    public AppointmentReport(){

    }

    public AppointmentReport(String month, String count, String type){
        this.month = month;
        this.count = count;
        this.type = type;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
