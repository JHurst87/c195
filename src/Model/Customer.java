package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class Customer {
    private int customerId;
    private String customerName;
    private int addressId;
    private Boolean active;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public Customer(int customerId, String customerName, int addressId, Boolean active, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy){
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getCustomerId(){
        return this.customerId;
    }

    public String getCustomerName(){
        return this.customerName;
    }

    public int getAddressId(){
        return this.addressId;
    }

    public Boolean getActive(){
        return this.active;
    }

    public Timestamp getCreateDate(){
        return this.createDate;
    }

    public String getCreatedBy(){
        return this.createdBy;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCreateDate(Timestamp createDate) {
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

    public Timestamp getlastUpdate(){
        return this.lastUpdate;
    }

    public String getlastUpdateBy(){
        return this.lastUpdateBy;
    }
}
