package models;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 22.03.13
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public enum Role implements be.objectify.deadbolt.core.models.Role{
    USER,
    ADMIN;

    @Override
    public String getName() {
        return name();
    }
   


}
