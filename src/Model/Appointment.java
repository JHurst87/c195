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

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
