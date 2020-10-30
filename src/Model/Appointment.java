package Model;

import DAO.CustomerDaoImpl;
import utils.DBConnection;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private User user;
    private String title;
    private String description;
    private String location;
    private String contact;
    private Customer customer;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;

    public Appointment() {
        this.appointmentId = 0;
        this.title = "";
        this.description = "";
        this.location = "";
        this.contact = "";
        this.type = "";
        this.url = "";
        this.start = null;
        this.end = null;
        this.user = null;
        this.customer = null;
    }

    public Appointment(
            int appointmentId,
            String title,
            String description,
            String location,
            String contact,
            String type,
            String url,
            LocalDateTime start,
            LocalDateTime end,
            User user,
            Customer customer
    ){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.user = user;
        this.customer = customer;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public boolean isValid(){
        if(this.customer == null){
            return false;
        }

        if(this.title.equals("")){
            return false;
        }

        if(this.description.equals("")){
            return false;
        }

        if (this.location.equals("")) {
            return false;
        }

        if (this.contact.equals("")) {
            return false;
        }

        if (this.url.equals("")) {
            return false;
        }

        if (this.start == null) {
            return false;
        }

        if (this.end == null) {
            return false;
        }

        return true;
    }
}
