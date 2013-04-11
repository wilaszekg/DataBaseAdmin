package models;

import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 11.04.13
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */

@Embeddable
public class UserInfo {
    private String firstName;
    private String secondName;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
