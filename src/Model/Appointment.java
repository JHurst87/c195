package Model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import DAO.AppointmentDaoImpl;
import Exception.AppointmentException;
import Exception.AppointmentTimeException;
import Exception.AppointmentOverlappingException;
import View_Controller.ModifyAppointmentController;
import javafx.collections.ObservableList;

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
    private static final int OPEN_TIME = 9;
    private static final int CLOSE_TIME = 17;

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

    public boolean isValid() throws AppointmentException, SQLException {
        if(this.customer == null){
            throw new AppointmentException("Please select a customer.");
        }

        if(this.title.equals("")){
            throw new AppointmentException("Title is required.");
        }

        if(this.description.equals("")){
            throw new AppointmentException("Description is required.");
        }

        if (this.contact.equals("")) {
            throw new AppointmentException("Consultant/Contact is required.");
        }

        if(this.type.equals("")){
            throw new AppointmentException("Type is required.");
        }

        if (this.url.equals("")) {
            throw new AppointmentException("URL is required.");
        }

        if (this.location.equals("")) {
            throw new AppointmentException("Location is required.");
        }

        if (this.start == null) {
            throw new AppointmentException("Start time is required.");
        }

        if (this.end == null) {
            throw new AppointmentException("End time is required.");
        }

        return isValidTime();
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isValidTime() throws AppointmentTimeException, SQLException {
        /*
        Time validation
        Rules:
        1. Appointment can be only made Monday through Saturday
        2. Appointment can only be held on 1 day, aka no spanning multiple days.
        3. Appointment can only be made during 9AM to 5PM Local Time
        4. Appointment end cannot before appointment start time
        */
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate startDay = this.start.toLocalDate();
        LocalDate endDay = this.end.toLocalDate();

        int dayOfWeek = startDay.getDayOfWeek().getValue();

        LocalTime startTime = this.start.toLocalTime();
        LocalTime endTime = this.end.toLocalTime();

        if(dayOfWeek == 0){
            throw new AppointmentTimeException("Appointments cannot be scheduled on Sundays");
        }

        if(!startDay.isEqual(endDay)){
            throw new AppointmentTimeException("Appointments must begin and end on the same day");
        }


        if( startTime.isBefore(midnight.plusHours(9)) || startTime.isAfter(midnight.plusHours(17)) ) {
            throw new AppointmentTimeException("Appointment must be made AFTER 9AM and ON or BEFORE 5PM Local Time");
        }

        if( endTime.isAfter( midnight.plusHours(17) ) || endTime.isBefore(midnight.plusHours(9)) ){
            throw new AppointmentTimeException("Appointment must be made BEFORE 5PM and ON or AFTER 9AM Local Time");
        }

        if(endTime.isBefore(startTime)){
            throw new AppointmentTimeException("Appointment end time must be AFTER start time");
        }

        if(endTime.equals(startTime)){
            throw new AppointmentTimeException("Appointment cannot start and end at the same time");
        }

        LocalDateTime start = LocalDateTime.of(startDay, startTime);
        LocalDateTime end = LocalDateTime.of(endDay, endTime);

//        //Process Overlapping appointments
//        if(!isValidOverlappingAppointments(start, end)){
//            throw new AppointmentOverlappingException("Contact/consultant has a conflicting appointment at this time.");
//        }

        return true;
    }

    public ObservableList<Appointment> getOverlappingAppointments() throws SQLException {
        //Uses time from appointments to check for conflicts
        return AppointmentDaoImpl.getOverlappingAppointments(this.getStart(), this.getEnd());
    }

}
