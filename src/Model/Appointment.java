package Model;

import DAO.CustomerDaoImpl;
import utils.DBConnection;
import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private String start;
    private String end;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private String customerName;
    private String userName;

    private Customer customer;

    public Appointment(
            int appointmentId,
            int customerId,
            int userId,
            String title,
            String description,
            String location,
            String contact,
            String type,
            String url,
            String start,
            String end,
            Date createDate,
            String createdBy,
            Timestamp lastUpdate,
            String lastUpdateBy,
            String customerName,
            String userName
    ){
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.customerName = customerName;
    }

    public int getAppointmentId(){
        return this.appointmentId;
    }

    public int getCustomerId(){
        return this.customerId;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getLocation(){
        return this.location;
    }

    public String getContact(){
        return this.contact;
    }

    public String getType(){
        return this.type;
    }

    public String getUrl(){
        return this.url;
    }

    public String getStart(){
        return this.start;
    }

    public String getEnd(){
        return this.end;
    }

    public Date getCreateDate(){
        return this.createDate;
    }

    public String getCreatedBy(){
        return this.createdBy;
    }

    public Timestamp getLastUpdate(){
        return this.lastUpdate;
    }

    public String getLastUpdateBy(){
        return this.lastUpdateBy;
    }

    public String getCustomerName(){
        return this.customerName;
    }
}
