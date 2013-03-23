package models;

/**
 *
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 22.03.13
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public enum Role implements be.objectify.deadbolt.core.models.Role{
    USER,
    ADMIN;

    /**
     * Text name of a role.
     * Implementation of deadbol-Role interface.
     * @return
     */
    @Override
    public String getName() {
        return name();
    }


}
