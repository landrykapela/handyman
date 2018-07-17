package tz.co.neelansoft.handyman;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by landre on 03/07/2018.
 */

public class HandyMan implements Serializable {

    private String id;
    private String name;
    private String email;
    private String service;
    private String description;
    private String mobile;
    private String city;
    private String country;
    private String address;

    //no argument constructor
    public HandyMan(){

    }

    //constructor with arguments
    public HandyMan(String id, String name, String email, String service, String description, String mobile, String address, String city, String country){
        this.id = id;
        this.name = name;
        this.email = email;
        this.service = service;
        this.description = description;
        this.mobile = mobile;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
