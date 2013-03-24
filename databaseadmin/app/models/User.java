package models;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Subject;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 22.03.13
 * Time: 09:59
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class User extends Model implements Subject {
    @Id
    public String login;

    public String password;
    @Enumerated
    public Role role;

    public static Model.Finder<String, User> find = new Model.Finder<String, User>(
            String.class, User.class
    );

    /**
     * Checks if there exist a user with given credentials.
     * Probably it will be moved to outside authentication service.
     * @param login
     * @param password
     * @return true when there exist a user with login & password given
     */
    public static boolean authenticate(String login, String password) {
        User user = findByLogin(login);
        if (user == null)
            return false;

        if (login.equals(user.login) && password.equals(user.password))
            return true;

        return false;
    }


    public static List<User> all() {
        return find.all();
    }

    public static void create(User user) {
        user.save();
    }

    public static void remove(String login) {
        find.ref(login).delete();
    }

    public static User findByLogin(String uLogin) {
        return find.byId(uLogin);
    }



    /*
    implementations of Subject interface
    used by DeadBolt
     */

    /**
     * Deadbolt helper. Returns list od user's roles.
     * @return
     */
    @Override
    public List<? extends be.objectify.deadbolt.core.models.Role> getRoles() {
        List<Role> list = new ArrayList<Role>();
        list.add(role);
        return list;
    }

    /**
     * Deadbolt helper. Not used as long as users have no permitions declared.
     * @return an empty list
     */
    @Override
    public List<? extends Permission> getPermissions() {
        return new ArrayList<Permission>();
    }

    /**
     * Deadbolt helper.
     * @return user's login
     */
    @Override
    public String getIdentifier() {
        return login;
    }
}
