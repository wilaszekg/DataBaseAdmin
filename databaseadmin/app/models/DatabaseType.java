package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 28.03.13
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class DatabaseType extends Model {
    @Id
    public int id;

    public String vendor;
    public String name;
    public String version;

    @OneToMany
    public List<DatabaseServer> databaseServers;

    public static Model.Finder<Integer, DatabaseType> find = new Finder<Integer, DatabaseType>(
            Integer.class, DatabaseType.class
    );

    public static List<DatabaseType> all() {
        return find.all();
    }

    public static void create(DatabaseType databaseType) {
        databaseType.save();
    }

    public static void remove(int typeId) {
        find.ref(typeId).delete();
    }

    public static DatabaseType findById(int typeId) {
        return find.byId(typeId);
    }

    public static int count() {
        return find.findRowCount();
    }
}
