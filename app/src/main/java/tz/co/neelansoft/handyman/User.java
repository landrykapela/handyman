package tz.co.neelansoft.handyman;

/**
 * Created by landre on 05/07/2018.
 */

public class User {

    private String userId;
    private String fullName;
    private String email;

    //public constructor with no arguments
    public User(){

    }

    //public constructor with arguments
    public User(String userId, String fullName, String email){
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
