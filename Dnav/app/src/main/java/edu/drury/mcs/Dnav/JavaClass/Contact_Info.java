package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by mark93 on 4/11/2016.
 */
public class Contact_Info {
    private String contact_name;
    private String phone_num;
    private String email;
    private String address;

    public Contact_Info(){}

    public Contact_Info(String name, String phone, String email, String address){
        this.contact_name = name;
        this.phone_num = phone;
        this.email = email;
        this.address = address;
    }


    //getters
    public String getContact_name() {return contact_name;}
    public String getPhone_num() {return phone_num;}
    public String getEmail() {return email;}
    public String getAddress() {return address;}

    //setters
    public void setContact_name(String newName) {contact_name = newName;}
    public void setPhone_num(String newPhone) {phone_num = newPhone;}
    public void setEmail(String newEmail) {email = newEmail;}
    public void setAddress(String newAddress) {address = newAddress;}


}
