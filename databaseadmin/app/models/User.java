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

    public static boolean authenticate(String login, String password) {//TODO: check if password is null
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
    @Override
    public List<? extends be.objectify.deadbolt.core.models.Role> getRoles() {
        List<Role> list = new ArrayList<Role>();
        list.add(role);
        return list;
    }

    @Override
    public List<? extends Permission> getPermissions() {
        return new ArrayList<Permission>();
    }

    @Override
    public String getIdentifier() {
        return login;
    }
}
