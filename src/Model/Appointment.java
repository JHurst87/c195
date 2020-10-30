package Model;

import java.time.LocalDateTime;
import Exception.AppointmentException;

public class Appointment {
    private int appointmentId;
    private int userId;
    private String contact;
    private String title;
    private String description;
    private String location;
    private Customer customer;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;

    public Appointment() {
        this.appointmentId = 0;
        this.userId = 0;
        this.contact = "";
        this.title = "";
        this.description = "";
        this.location = "";
        this.type = "";
        this.url = "";
        this.start = null;
        this.end = null;
        this.customer = null;
    }

    public Appointment(
            int appointmentId,
            int userId,
            String title,
            String contact,
            String description,
            String location,
            String type,
            String url,
            LocalDateTime start,
            LocalDateTime end,
            Customer customer
    ){
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.contact = contact;
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

    public boolean isValid() throws AppointmentException {
        if(this.customer == null){
            throw new AppointmentException("Customer choice is required.");
        }

        if(this.title.equals("")){
            throw new AppointmentException("Title is required.");
        }

        if(this.description.equals("")){
            throw new AppointmentException("Title is required.");
        }

        if (this.location.equals("")) {
            throw new AppointmentException("Location is required.");
        }

        if (this.contact.equals("")) {
            throw new AppointmentException("Consultant is required.");
        }

        if (this.url.equals("")) {
            throw new AppointmentException("URL is required.");
        }

        if (this.start == null) {
            throw new AppointmentException("Start time is required.");
        }

        if (this.end == null) {
            throw new AppointmentException("End time is required.");
        }



        return true;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
