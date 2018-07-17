package tz.co.neelansoft.handyman;

/**
 * Created by landre on 02/07/2018.
 */

public class HandymanEntry {
    private String name;
    private String email;
    private String service;
    private String serviceDescription;


    public HandymanEntry() {
    }

    public HandymanEntry(String name, String email, String service, String serviceDescription) {
        this.name = name;
        this.email = email;
        this.service = service;
        this.serviceDescription = serviceDescription;
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

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}
