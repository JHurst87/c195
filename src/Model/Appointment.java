package Model;

import utils.DBConnection;
import java.sql.Date;

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
    private Date lastUpdate;
    private String lastUpdateBy;

    private Customer customer;

    public Appointment(
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
            Date lastUpdate,
            String lastUpdateBy
    ){
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
    }
}
