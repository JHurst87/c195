package Model;

import Exception.CustomerException;
public class Customer {
    private int customerId;
    private int addressId;
    private String customerName;
    private Boolean active;
    private Address address;

    public Customer(){
        this.customerId = 0;
        this.customerName = "";
        this.active = false;
        this.address = null;
    }

    public Customer(int customerId, int addressId,  String customerName, Boolean active, Address address){
        this.customerId = customerId;
        this.customerName = customerName;
        this.active = active;
        this.address = address;
    }

    public boolean isValid() throws CustomerException {
        if(this.customerName.equals("")){
            throw new CustomerException("Customer name is required.");
        }

        if(this.address == null){
            throw new CustomerException("Customer has invalid address");
        }

        return true;
    }

    public int getCustomerId(){
        return this.customerId;
    }

    public String getCustomerName(){
        return this.customerName;
    }

    public Boolean getActive(){
        return this.active;
    }


    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Address getAddress(){
        return this.address;
    }

    public String toString(){
        return customerName;
    }
}
